package com.github.adnanrangrej.backend

fun getApiKey(): String {
    // Try to get the API_KEY from the system environment variables.
    return System.getenv("GNEWS_API_KEY") ?: throw Exception("NEWS API_KEY is missing")
}

fun getSnsTopicArn(): String {
    return System.getenv("SNS_TOPIC_ARN") ?: throw Exception("SNS_TOPIC_ARN is missing")
}

fun getRegion(): String {
    return System.getenv("AWS_REGION") ?: throw Exception("AWS_REGION is missing")
}

fun getPlatformApplicationArn(): String {
    return System.getenv("PLATFORM_APPLICATION_ARN")
        ?: throw Exception("PLATFORM_APPLICATION_ARN is missing")
}

fun getNewsArticlesTableName(): String {
    return System.getenv("DYNAMODB_NEWSARTICLES_TABLE_NAME")
        ?: throw Exception("News Articles Table name is missing")
}

fun getNewsMetaDataTableName(): String {
    return System.getenv("DYNAMODB_NEWMETADATA_TABLE_NAME")
        ?: throw Exception("News Meta Data Table name is missing")
}