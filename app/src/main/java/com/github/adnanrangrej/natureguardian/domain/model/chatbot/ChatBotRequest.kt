package com.github.adnanrangrej.natureguardian.domain.model.chatbot

data class ChatBotRequest(
    val history: List<Message> = listOf(),
    val prompt: String,
    val systemInstruction: String
)