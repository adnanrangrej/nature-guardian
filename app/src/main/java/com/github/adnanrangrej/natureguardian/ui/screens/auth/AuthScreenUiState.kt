package com.github.adnanrangrej.natureguardian.ui.screens.auth

data class AuthScreenUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val errorMessage: String? = null
)