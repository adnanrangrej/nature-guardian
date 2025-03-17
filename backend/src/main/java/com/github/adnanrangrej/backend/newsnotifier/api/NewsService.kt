package com.github.adnanrangrej.backend.newsnotifier.api

import com.github.adnanrangrej.backend.newsnotifier.model.NewsApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v4/search")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("country") country: String = "in",
        @Query("from") from: String?,
        @Query("sortby") sortBy: String = "publishedAt",
        @Query("apikey") apiKey: String
    ): NewsApiResponse
}