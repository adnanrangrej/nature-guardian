package com.github.adnanrangrej.apigateway

import io.github.cdimascio.dotenv.Dotenv

fun getRegion(): String {
    return System.getenv("AWS_REGION") ?: Dotenv.load()["AWS_REGION"] ?: "us-east-1"
}
fun getNewsArticlesTableName(): String {
    return System.getenv("DYNAMODB_NEWSARTICLES_TABLE_NAME") ?: Dotenv.load()["DYNAMODB_NEWSARTICLES_TABLE_NAME"] ?: throw Exception("News Articles Table name is missing")
}

