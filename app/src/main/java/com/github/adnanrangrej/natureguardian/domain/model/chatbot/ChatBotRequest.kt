package com.github.adnanrangrej.natureguardian.domain.model.chatbot

data class ChatBotRequest(
    val history: List<History> = listOf(),
    val prompt: String
)