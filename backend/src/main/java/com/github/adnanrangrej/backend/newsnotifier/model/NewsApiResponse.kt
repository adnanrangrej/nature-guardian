package com.github.adnanrangrej.backend.newsnotifier.model

data class NewsApiResponse(
    val articles: List<Article> = listOf(),
    val totalArticles: Int = 0
)