package com.github.adnanrangrej.natureguardian.ui.screens.auth.signup

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isInputValid: Boolean = false,
    val passwordVisibility: Boolean = false,
    val confirmPasswordVisibility: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val errorMessage: String? = null
)