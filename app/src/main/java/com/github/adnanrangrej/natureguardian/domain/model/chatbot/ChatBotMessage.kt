package com.github.adnanrangrej.natureguardian.domain.model.chatbot

data class ChatBotMessage(
    val statusCode: Int,
    val requestId: String,
    val isError: Boolean,
    val errorMessage: String?,
    val responseText: String?,
    val role: String?
)
