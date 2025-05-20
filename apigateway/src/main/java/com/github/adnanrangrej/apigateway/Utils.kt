package com.github.adnanrangrej.apigateway

fun getRegion(): String {
    return System.getenv("AWS_REGION") ?: throw Exception("AWS_REGION is missing")
}

fun getNewsArticlesTableName(): String {
    return System.getenv("DYNAMODB_NEWSARTICLES_TABLE_NAME")
        ?: throw Exception("News Articles Table name is missing")
}

