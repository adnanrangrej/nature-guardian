package com.github.adnanrangrej.natureguardian.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.domain.model.auth.AuthResult
import com.github.adnanrangrej.natureguardian.domain.usecase.auth.LoginUseCase
import com.github.adnanrangrej.natureguardian.domain.usecase.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthScreenUiState>(AuthScreenUiState())
    val uiState: StateFlow<AuthScreenUiState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    isAuthenticated = false
                )
            }
            loginUseCase(email, password).collect { result ->
                when (result) {
                    is AuthResult.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }

                    AuthResult.Loading -> {}
                    is AuthResult.Success -> {
                        _uiState.update { it.copy(isAuthenticated = true, isLoading = false) }
                    }
                }

            }
        }
    }

    fun signUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            signUpUseCase(email, password, name).collect { result ->
                when (result) {
                    is AuthResult.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }

                    AuthResult.Loading -> {}
                    is AuthResult.Success -> {
                        _uiState.update { it.copy(isAuthenticated = true, isLoading = false) }
                    }
                }
            }
        }
    }
}