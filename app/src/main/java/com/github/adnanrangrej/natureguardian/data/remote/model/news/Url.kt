package com.github.adnanrangrej.natureguardian.data.remote.model.news


import com.google.gson.annotations.SerializedName

data class Url(
    @SerializedName("value")
    val value: String = ""
)