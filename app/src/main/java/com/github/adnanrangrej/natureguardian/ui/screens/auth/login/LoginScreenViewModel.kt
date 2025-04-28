package com.github.adnanrangrej.natureguardian.ui.screens.auth.login

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.domain.model.auth.AuthResult
import com.github.adnanrangrej.natureguardian.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState


    // ------ Validation Functions ------
    private fun validateEmail(email: String): String? {
        return if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Invalid email address"
        } else {
            null
        }
    }

    private fun validatePassword(password: String): String? {
        return if (password.isBlank() || password.length < 6) {
            "Password must be at least 6 characters"
        } else {
            null
        }
    }

    // ------ Helper to determine overall form validity ------
    private fun checkFormValidity(
        emailError: String?,
        passwordError: String?
    ) = emailError == null && passwordError == null

    // ------ Event Handlers ------
    fun onEmailChange(email: String) {
        val emailError = validateEmail(email)
        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = emailError,
            isInputValid = checkFormValidity(
                emailError = emailError,
                passwordError = _uiState.value.passwordError
            )
        )
    }

    fun onPasswordChange(password: String) {
        val passwordError = validatePassword(password)
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = passwordError,
            isInputValid = checkFormValidity(
                emailError = _uiState.value.emailError,
                passwordError = passwordError
            )
        )
    }

    fun onPasswordVisibilityChange() {
        _uiState.value = _uiState.value.copy(
            passwordVisibility = !_uiState.value.passwordVisibility
        )
    }

    fun login() {
        viewModelScope.launch {
            loginUseCase(_uiState.value.email, _uiState.value.password).collect { result ->
                when (result) {
                    is AuthResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }

                    AuthResult.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true
                        )
                    }

                    is AuthResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isAuthenticated = true
                        )
                    }
                }
            }
        }
    }
}