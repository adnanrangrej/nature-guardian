package com.github.adnanrangrej.natureguardian.data.remote.model.news


import com.google.gson.annotations.SerializedName

data class SourceName(
    @SerializedName("value")
    val value: String = ""
)