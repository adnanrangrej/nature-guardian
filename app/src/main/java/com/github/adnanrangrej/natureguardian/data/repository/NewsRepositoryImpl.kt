package com.github.adnanrangrej.natureguardian.data.repository

import com.github.adnanrangrej.natureguardian.data.mapper.toNewsItem
import com.github.adnanrangrej.natureguardian.data.mapper.toNewsResponse
import com.github.adnanrangrej.natureguardian.data.remote.api.news.NewsApiService
import com.github.adnanrangrej.natureguardian.domain.model.news.NewsItem
import com.github.adnanrangrej.natureguardian.domain.model.news.NewsResponse
import com.github.adnanrangrej.natureguardian.domain.repository.NewsRepository

class NewsRepositoryImpl(private val newsApiService: NewsApiService) : NewsRepository {
    override suspend fun getAllNews(): NewsResponse = newsApiService.getAllNews().toNewsResponse()

    override suspend fun getAllNewsNextPage(nextToken: String): NewsResponse =
        newsApiService.getAllNewsNextPage(nextToken).toNewsResponse()

    override suspend fun getNewsItem(publishedAt: String): NewsItem =
        newsApiService.getNewsItem(publishedAt).toNewsItem()
}