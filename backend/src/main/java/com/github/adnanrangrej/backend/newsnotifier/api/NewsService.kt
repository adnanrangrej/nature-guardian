package com.github.adnanrangrej.backend.newsnotifier.api

import com.github.adnanrangrej.backend.newsnotifier.model.NewsApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v4/search")
    fun getNews(
        @Query("q") query: String,
        @Query("country") country: String = "in",
        @Query("from") from: String?,
        @Query("max") max: Int = 10,
        @Query("sortby") sortBy: String = "publishedAt",
        @Query("apikey") apiKey: String
    ): Call<NewsApiResponse>
}