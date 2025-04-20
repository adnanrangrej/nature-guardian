package com.github.adnanrangrej.natureguardian.data.remote.api.chatbot

import com.github.adnanrangrej.natureguardian.data.remote.model.chatbot.ChatBotResponse
import com.github.adnanrangrej.natureguardian.domain.model.chatbot.ChatBotRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatBotApiService {
    @POST("nature-bot")
    suspend fun getChatBotResponse(@Body chatBotRequest: ChatBotRequest): ChatBotResponse
}