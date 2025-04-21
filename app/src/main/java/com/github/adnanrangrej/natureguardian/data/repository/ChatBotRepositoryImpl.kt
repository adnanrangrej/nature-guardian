package com.github.adnanrangrej.natureguardian.data.repository

import com.github.adnanrangrej.natureguardian.data.mapper.toChatBotMessage
import com.github.adnanrangrej.natureguardian.data.remote.api.chatbot.ChatBotApiService
import com.github.adnanrangrej.natureguardian.domain.model.chatbot.ChatBotMessage
import com.github.adnanrangrej.natureguardian.domain.model.chatbot.ChatBotRequest
import com.github.adnanrangrej.natureguardian.domain.repository.ChatBotRepository
import javax.inject.Inject

class ChatBotRepositoryImpl @Inject constructor(
    private val chatBotApiService: ChatBotApiService
) : ChatBotRepository {
    override suspend fun getChatBotResponse(request: ChatBotRequest): ChatBotMessage =
        chatBotApiService.getChatBotResponse(request).toChatBotMessage()

    override suspend fun initializeChatBot(
        initialPrompt: String,
        systemInstruction: String
    ): ChatBotMessage =
        chatBotApiService.getChatBotResponse(
            ChatBotRequest(
                prompt = initialPrompt,
                systemInstruction = systemInstruction
            )
        ).toChatBotMessage()
}