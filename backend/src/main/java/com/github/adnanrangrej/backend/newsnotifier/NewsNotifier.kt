package com.github.adnanrangrej.backend.newsnotifier

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.GetItemRequest
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.PublishRequest
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.github.adnanrangrej.backend.getApiKey
import com.github.adnanrangrej.backend.getNewsArticlesTableName
import com.github.adnanrangrej.backend.getNewsMetaDataTableName
import com.github.adnanrangrej.backend.getRegion
import com.github.adnanrangrej.backend.getSnsTopicArn
import com.github.adnanrangrej.backend.newsnotifier.api.NewsService
import com.github.adnanrangrej.backend.newsnotifier.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class NewsNotifier : RequestHandler<Any, String> {

    private val newsArticleTableName = getNewsArticlesTableName()

    private val newsMetaDataTableName = getNewsMetaDataTableName()

    // News API key
    private val apiKey = getApiKey()

    private val snsTopicArn = getSnsTopicArn()

    private val region = getRegion()

    // Retrofit setup to call the 'Gnews' Api
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://gnews.io/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: NewsService = retrofit.create(NewsService::class.java)


    override fun handleRequest(input: Any, context: Context): String {
        return runBlocking {
            withContext(Dispatchers.IO) {
                val logger = context.logger

                logger.log("NewsNotifer Lambda Triggered!!\n")
                // Fetch lastPublishedAt from DB
                val lastPublishedAt = getLastPublishedAt()
                logger.log("Fetched lastPublishedAt from DynamoDB table: $lastPublishedAt\n")

                // Ensure we have a valid timestamp. If null, use a default value.
                val parsedInstant = if (lastPublishedAt != null) {
                    Instant.parse(lastPublishedAt)
                } else {
                    Instant.now().minus(1, ChronoUnit.DAYS)
                }

                //add 1 sec
                val newInstant = parsedInstant.plus(1, ChronoUnit.SECONDS)

                val formattedTimestamp = DateTimeFormatter.ISO_INSTANT.format(newInstant)
                logger.log("from publishedAt timestamp date: $formattedTimestamp\n")

                // Fetch latest articles using the lastPublishedAt as a filter.
                val articles = fetchLatestNews(formattedTimestamp)
                logger.log("Fetched ${articles.size} articles from API\n")

                if (articles.isEmpty()) {
                    logger.log("No new articles found.\n")
                    return@withContext "No new articles."
                }

                // Track the newest timestamp found among the new articles.
                var newTimestamp = lastPublishedAt ?: ""
                articles.forEach { article ->
                    storeArticle(article)

                    val jsonPayload = """
    {
      "default": "{\"title\": \"${article.title}\", \"body\": \"${article.description}\", \"imgUrl\": \"${article.image}\", \"url\": \"${article.url}\"}",
      "GCM": "{\"notification\": {\"title\": \"${article.title}\", \"body\": \"${article.description}\", \"click_action\": \"OPEN_MAIN_ACTIVITY\"}}"
    }
""".trimIndent()

                    publishNotification(jsonPayload)
                }
                newTimestamp = articles[0].publishedAt

                // If a newer article is found, update the metadata and send a notification.
                if (newTimestamp != lastPublishedAt && newTimestamp.isNotEmpty()) {
                    updateLastPublishedAt(newTimestamp)
                    logger.log("Updated lastPublishedAt to $newTimestamp\n")
                } else {
                    logger.log("No update to lastPublishedAt needed.\n")
                }

                logger.log("NewsNotifierLambda completed.\n")
                return@withContext "Processed ${articles.size} articles."
            }
        }
    }

    // Fetches the "lastPublishedAt" record from DynamoDB
    private suspend fun getLastPublishedAt(): String? {
        val key = mapOf("id" to AttributeValue.S("lastPublishedAt"))

        val request = GetItemRequest {
            this.key = key
            tableName = newsMetaDataTableName
        }
        val result = DynamoDbClient { region = this@NewsNotifier.region }.use { ddb ->
            ddb.getItem(request)
        }
        return result.item?.get("timestamp")?.asS()
    }

    // Update the "lastPublishedAt" record in DynamoDB
    private suspend fun updateLastPublishedAt(newTimeStamp: String) {
        val item = mapOf(
            "id" to AttributeValue.S("lastPublishedAt"),
            "timestamp" to AttributeValue.S(newTimeStamp)
        )
        val request = PutItemRequest {
            tableName = newsMetaDataTableName
            this.item = item
        }
        DynamoDbClient { region = this@NewsNotifier.region }.use { ddb ->
            ddb.putItem(request)
        }
    }

    // Put new articles in DynamoDB table
    private suspend fun storeArticle(article: Article) {
        val item = mapOf(
            "id" to AttributeValue.S("News"),
            "publishedAt" to AttributeValue.S(article.publishedAt),
            "title" to AttributeValue.S(article.title),
            "description" to AttributeValue.S(article.description),
            "content" to AttributeValue.S(article.content),
            "url" to AttributeValue.S(article.url),
            "image" to AttributeValue.S(article.image),
            "sourceName" to AttributeValue.S(article.source.name),
            "sourceUrl" to AttributeValue.S(article.source.url)
        )
        val request = PutItemRequest {
            tableName = newsArticleTableName
            this.item = item
        }
        DynamoDbClient { region = this@NewsNotifier.region }.use { ddb ->
            ddb.putItem(request)
        }
    }

    // Publish new messages to SNS topic
    private suspend fun publishNotification(message: String) {
        try {
            val request = PublishRequest {
                topicArn = snsTopicArn
                this.message = message
                subject = "New Conservation News"
            }

            val response = SnsClient { region = this@NewsNotifier.region }.use { sns ->
                sns.publish(request)
            }
            println("SNS Publish Response: MessageId=${response.messageId}")
        } catch (e: Exception) {
            println("Error publishing SNS notification: ${e.message}")
        }
    }

    // Fetch the latest news from Gnews API using retrofit service
    private suspend fun fetchLatestNews(lastPublishedAt: String?): List<Article> {
        val query = "biodiversity OR conservation"

        return try {
            val response = service.getNews(
                query = query,
                from = lastPublishedAt,
                apiKey = apiKey
            )
            response.articles
        } catch (e: IOException) {
            println("Network error while fetching news: ${e.message}")
            emptyList()
        } catch (e: Exception) {
            println("Unexpected error: ${e.localizedMessage}")
            emptyList()
        }
    }
}