package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail.geminichat

import com.github.adnanrangrej.natureguardian.domain.model.species.Message

sealed interface ChatBotUiState {
    object Loading : ChatBotUiState
    data class Success(val messages: List<Message>) : ChatBotUiState
    data class Error(val errorMessage: String) : ChatBotUiState
}