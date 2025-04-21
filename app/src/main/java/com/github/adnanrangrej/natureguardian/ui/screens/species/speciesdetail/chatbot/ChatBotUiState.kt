package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail.chatbot

import com.github.adnanrangrej.natureguardian.domain.model.chatbot.Message

sealed interface ChatBotUiState {
    object Loading : ChatBotUiState
    data class Success(
        val messages: List<Message>,
        val isLoading: Boolean,
        val isError: Boolean,
        val errorMessage: String?
    ) : ChatBotUiState

    data class Error(val errorMessage: String) : ChatBotUiState
}