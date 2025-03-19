package com.github.adnanrangrej.natureguardian.domain.model.news

data class NewsResponse(
    val items: List<NewsItem> = listOf(),
    val nextToken: String? = null
)
