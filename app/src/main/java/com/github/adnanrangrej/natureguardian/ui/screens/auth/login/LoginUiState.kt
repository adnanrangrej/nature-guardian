package com.github.adnanrangrej.natureguardian.ui.screens.auth.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isInputValid: Boolean = false,
    val passwordVisibility: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val errorMessage: String? = null
)