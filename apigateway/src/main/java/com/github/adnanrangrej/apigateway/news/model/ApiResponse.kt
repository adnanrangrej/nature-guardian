package com.github.adnanrangrej.apigateway.news.model

data class ApiResponse(
    val statusCode: Int,
    val body: String,
    val headers: Map<String, String> = mapOf("Content-Type" to "application/json")
)