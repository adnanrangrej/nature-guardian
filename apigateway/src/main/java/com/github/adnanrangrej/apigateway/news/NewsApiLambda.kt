package com.github.adnanrangrej.apigateway.news

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.GetItemRequest
import aws.sdk.kotlin.services.dynamodb.model.QueryRequest
import aws.smithy.kotlin.runtime.text.encoding.decodeBase64
import aws.smithy.kotlin.runtime.text.encoding.encodeBase64
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.github.adnanrangrej.apigateway.getNewsArticlesTableName
import com.github.adnanrangrej.apigateway.getRegion
import com.github.adnanrangrej.apigateway.news.model.ApiResponse
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking

class NewsApiLambda : RequestHandler<Map<String, Any>, ApiResponse> {

    private val newsArticlesTableName = getNewsArticlesTableName()
    private val region = getRegion()
    private val gson = Gson()

    @Suppress("UNCHECKED_CAST")
    override fun handleRequest(event: Map<String, Any>, context: Context): ApiResponse {
        val logger = context.logger
        logger.log("News API Request Received")

        val path = event["path"] as? String ?: ""
        val pathParams = event["pathParameters"] as? Map<String, String>
        val queryParams = event["queryStringParameters"] as? Map<String, String>
        val nextToken = queryParams?.get("nextToken")

        logger.log("Path: $path, NextToken: $nextToken")

        return runBlocking {
            try {
                when {
                    path == "/news" -> {
                        val (items, lastEvaluatedKey) = getAllItems(nextToken)
                        logger.log("Fetched ${items.size} items from DynamoDB.")
                        val response = mapOf(
                            "items" to items,
                            "nextToken" to lastEvaluatedKey?.let {
                                logger.log("Encoded Key: ${encodeKey(it)}")
                                encodeKey(it)
                            }
                        )
                        ApiResponse(200, gson.toJson(response))
                    }

                    path.startsWith("/news/") -> {
                        val publishedAt =
                            pathParams?.get("publishedAt") ?: path.substringAfter("/news/")
                        val item = getItem(publishedAt)
                        logger.log("Fetched item from DynamoDB: $item")
                        item?.let { ApiResponse(200, gson.toJson(it)) } ?: ApiResponse(
                            404,
                            "News item not found"
                        )
                    }

                    else -> ApiResponse(404, "Invalid request")
                }
            } catch (e: Exception) {
                logger.log("Error: ${e.localizedMessage}")
                ApiResponse(500, "Internal Server Error")
            }
        }
    }

    private suspend fun getItem(publishedAt: String): Map<String, AttributeValue>? {
        val request = GetItemRequest {
            tableName = newsArticlesTableName
            key = mapOf(
                "id" to AttributeValue.S("News"),
                "publishedAt" to AttributeValue.S(publishedAt)
            )
        }
        return DynamoDbClient {
            region = this@NewsApiLambda.region
        }.use { it.getItem(request).item }
    }

    private suspend fun getAllItems(nextToken: String?): Pair<List<Map<String, AttributeValue>>, Map<String, AttributeValue>?> {
        val exclusiveStartKey = decodeKey(nextToken)
        val request = QueryRequest {
            tableName = newsArticlesTableName
            keyConditionExpression = "id = :id"
            expressionAttributeValues = mapOf(":id" to AttributeValue.S("News"))
            limit = 20
            scanIndexForward = false
            exclusiveStartKey?.let { this.exclusiveStartKey = it }
        }

        val response =
            DynamoDbClient { region = this@NewsApiLambda.region }.use { it.query(request) }
        return response.items.orEmpty() to response.lastEvaluatedKey.takeUnless { it.isNullOrEmpty() }
    }

    private fun encodeKey(key: Map<String, AttributeValue>?): String? {
        key?.let {
            val json = gson.toJson(key)
            val encodedKey = json.encodeBase64()
            return encodedKey
        }
        return null
    }

    @Suppress("UNCHECKED_CAST")
    private fun decodeKey(encodedKey: String?): Map<String, AttributeValue>? {
        encodedKey?.let {
            val decodedJson = it.decodeBase64()
            val map =
                gson.fromJson(decodedJson, Map::class.java) as Map<String, Map<String, String>>
            return map.mapValues { (_, valueMap) -> AttributeValue.S(valueMap["value"]!!) }
        }
        return null
    }
}
