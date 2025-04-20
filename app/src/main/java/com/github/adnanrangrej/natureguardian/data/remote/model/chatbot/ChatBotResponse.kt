package com.github.adnanrangrej.natureguardian.data.remote.model.chatbot


import com.google.gson.annotations.SerializedName

data class ChatBotResponse(
    @SerializedName("body")
    val body: Body,
    @SerializedName("request_id")
    val requestId: String,
    @SerializedName("statusCode")
    val statusCode: Int
)