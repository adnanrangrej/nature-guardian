package com.github.adnanrangrej.natureguardian.data.remote.model.news


import com.google.gson.annotations.SerializedName

data class NewsApiResponse(
    @SerializedName("items")
    val items: List<Item> = listOf(),
    @SerializedName("nextToken")
    val nextToken: String? = null
)