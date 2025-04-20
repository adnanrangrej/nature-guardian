package com.github.adnanrangrej.natureguardian.domain.repository

import com.github.adnanrangrej.natureguardian.domain.model.chatbot.ChatBotMessage
import com.github.adnanrangrej.natureguardian.domain.model.chatbot.ChatBotRequest

interface ChatBotRepository {
    suspend fun getChatBotResponse(request: ChatBotRequest): ChatBotMessage
    suspend fun initializeChatBot(initialPrompt: String): ChatBotMessage
}