package com.github.adnanrangrej.backend

import io.github.cdimascio.dotenv.Dotenv

fun getApiKey(): String {
    // Try to get the API_KEY from the system environment variables.
    // If it's not set, load from the .env file.
    return System.getenv("GNEWS_API_KEY") ?: Dotenv.load()["GNEWS_API_KEY"] ?: throw Exception("NEWS API_KEY is missing")
}

fun getSnsTopicArn(): String {
    return System.getenv("SNS_TOPIC_ARN") ?: Dotenv.load()["SNS_TOPIC_ARN"] ?: throw Exception("SNS_TOPIC_ARN is missing")
}

fun getRegion(): String {
    return System.getenv("AWS_REGION") ?: Dotenv.load()["AWS_REGION"] ?: "us-east-1"
}

fun getPlatformApplicationArn(): String {
    return System.getenv("PLATFORM_APPLICATION_ARN") ?: Dotenv.load()["PLATFORM_APPLICATION_ARN"] ?: throw Exception("PLATFORM_APPLICATION_ARN is missing")
}

fun getNewsArticlesTableName(): String {
    return System.getenv("DYNAMODB_NEWSARTICLES_TABLE_NAME") ?: Dotenv.load()["DYNAMODB_NEWSARTICLES_TABLE_NAME"] ?: throw Exception("Table name is missing")
}