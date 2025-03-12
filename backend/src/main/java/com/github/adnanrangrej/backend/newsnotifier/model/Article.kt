package com.github.adnanrangrej.backend.newsnotifier.model

data class Article(
    val content: String = "",
    val description: String = "",
    val image: String = "",
    val publishedAt: String = "",
    val source: Source = Source(),
    val title: String = "",
    val url: String = ""
)