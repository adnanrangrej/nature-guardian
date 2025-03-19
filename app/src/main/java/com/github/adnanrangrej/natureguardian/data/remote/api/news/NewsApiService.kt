package com.github.adnanrangrej.natureguardian.data.remote.api.news

import com.github.adnanrangrej.natureguardian.data.remote.model.news.Item
import com.github.adnanrangrej.natureguardian.data.remote.model.news.NewsApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {
    @GET("news")
    suspend fun getAllNews(): NewsApiResponse

    @GET("news")
    suspend fun getAllNewsNextPage(@Query("nextToken") nextToken: String): NewsApiResponse

    @GET("news/{publishedAt}")
    suspend fun getNewsItem(@Path("publishedAt") publishedAt: String): Item
}