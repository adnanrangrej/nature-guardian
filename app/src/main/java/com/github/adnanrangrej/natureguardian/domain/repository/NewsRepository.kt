package com.github.adnanrangrej.natureguardian.domain.repository

import com.github.adnanrangrej.natureguardian.domain.model.news.NewsItem
import com.github.adnanrangrej.natureguardian.domain.model.news.NewsResponse

interface NewsRepository {
    suspend fun getAllNews(): NewsResponse
    suspend fun getAllNewsNextPage(nextToken: String): NewsResponse
    suspend fun getNewsItem(publishedAt: String): NewsItem
}