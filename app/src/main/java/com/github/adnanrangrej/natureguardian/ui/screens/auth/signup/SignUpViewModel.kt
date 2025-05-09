package com.github.adnanrangrej.natureguardian.ui.screens.auth.signup

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.domain.model.auth.AuthResult
import com.github.adnanrangrej.natureguardian.domain.usecase.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _uiState = mutableStateOf(SignUpUiState())
    val uiState: State<SignUpUiState> = _uiState

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

    private fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        return if (confirmPassword.isBlank() || password != confirmPassword) {
            "Passwords do not match"
        } else {
            null
        }
    }

    // ------ Helper to determine overall form validity ------
    private fun checkFormValidity(
        name: String,
        emailError: String?,
        passwordError: String?,
        confirmPasswordError: String?
    ) = name.isNotBlank() &&
            emailError == null &&
            passwordError == null &&
            confirmPasswordError == null

    // ------ Event Handlers ------
    fun onNameChange(newName: String) {
        _uiState.value = _uiState.value.copy(
            name = newName,
            isInputValid = checkFormValidity(
                name = newName,
                emailError = _uiState.value.emailError,
                passwordError = _uiState.value.passwordError,
                confirmPasswordError = _uiState.value.confirmPasswordError
            )
        )
    }

    fun onEmailChange(newEmail: String) {
        val emailError = validateEmail(newEmail)
        _uiState.value = _uiState.value.copy(
            email = newEmail,
            emailError = emailError,
            isInputValid = checkFormValidity(
                name = _uiState.value.name,
                emailError = emailError,
                passwordError = _uiState.value.passwordError,
                confirmPasswordError = _uiState.value.confirmPasswordError
            )
        )
    }

    fun onPasswordChange(newPassword: String) {

        val passwordError = validatePassword(newPassword)

        val confirmPasswordError = validateConfirmPassword(
            password = newPassword,
            confirmPassword = _uiState.value.confirmPassword
        )

        _uiState.value = _uiState.value.copy(
            password = newPassword,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError,
            isInputValid = checkFormValidity(
                name = _uiState.value.name,
                emailError = _uiState.value.emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            )
        )
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {

        val confirmPasswordError = validateConfirmPassword(
            password = _uiState.value.password,
            confirmPassword = newConfirmPassword
        )

        _uiState.value = _uiState.value.copy(
            confirmPassword = newConfirmPassword,
            confirmPasswordError = confirmPasswordError,
            isInputValid = checkFormValidity(
                name = _uiState.value.name,
                emailError = _uiState.value.emailError,
                passwordError = _uiState.value.passwordError,
                confirmPasswordError = confirmPasswordError
            )
        )
    }

    fun onPasswordVisibilityChange() {
        _uiState.value = _uiState.value.copy(
            passwordVisibility = !_uiState.value.passwordVisibility
        )
    }

    fun onConfirmPasswordVisibilityChange() {
        _uiState.value = _uiState.value.copy(
            confirmPasswordVisibility = !_uiState.value.confirmPasswordVisibility
        )
    }

    fun signUp() {

        _uiState.value = _uiState.value.copy(errorMessage = null)
        // Perform a final check on the current state before proceeding
        val currentState = _uiState.value
        val name = currentState.name
        val email = currentState.email
        val password = currentState.password
        val confirmPassword = currentState.confirmPassword


        val emailError = validateEmail(email)
        val passwordError = validatePassword(password)
        val confirmPasswordError = validateConfirmPassword(password, confirmPassword)

        val isFormCurrentlyValid = checkFormValidity(
            name = name,
            emailError = emailError,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError
        ) && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()

        if (!isFormCurrentlyValid) {
            _uiState.value = currentState.copy(
                emailError = emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError,
                isInputValid = false,
                errorMessage = "Please fill all fields correctly."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, errorMessage = null)
            signUpUseCase(
                email = _uiState.value.email,
                password = _uiState.value.password,
                name = _uiState.value.name
            ).collect { result ->
                when (result) {
                    is AuthResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }

                    AuthResult.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }

                    is AuthResult.Success -> {
                        _uiState.value =
                            _uiState.value.copy(isLoading = false, isAuthenticated = true)
                    }
                }
            }
        }
    }
}