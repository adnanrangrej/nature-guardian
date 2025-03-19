package com.github.adnanrangrej.natureguardian.data.remote.model.news


import com.google.gson.annotations.SerializedName

data class PublishedAt(
    @SerializedName("value")
    val value: String = ""
)