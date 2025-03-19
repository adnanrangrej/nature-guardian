package com.github.adnanrangrej.natureguardian.domain.model.news

data class NewsItem(
    val content: String,
    val description: String,
    val id: String,
    val image: String,
    val publishedAt: String,
    val sourceName: String,
    val sourceUrl: String,
    val title: String,
    val url: String
)
