package com.github.adnanrangrej.natureguardian.data.remote.model.chatbot


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("role")
    val role: String,
    @SerializedName("text")
    val text: String
)