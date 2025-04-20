package com.github.adnanrangrej.natureguardian.domain.usecase.chatbot

import com.github.adnanrangrej.natureguardian.domain.repository.ChatBotRepository
import javax.inject.Inject

class InitializeChatBotUseCase @Inject constructor(private val chatBotRepository: ChatBotRepository) {
    suspend operator fun invoke(initialPrompt: String) =
        chatBotRepository.initializeChatBot(initialPrompt)
}