package com.github.adnanrangrej.natureguardian.domain.usecase.chatbot

import com.github.adnanrangrej.natureguardian.domain.model.chatbot.ChatBotRequest
import com.github.adnanrangrej.natureguardian.domain.repository.ChatBotRepository
import javax.inject.Inject

class GetChatBotResponseUseCase @Inject constructor(private val chatBotRepository: ChatBotRepository) {
    suspend operator fun invoke(request: ChatBotRequest) =
        chatBotRepository.getChatBotResponse(request)
}