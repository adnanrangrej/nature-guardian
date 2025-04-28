package com.github.adnanrangrej.natureguardian.ui.screens.auth.signup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }

    SignUpScreenBody(
        modifier = Modifier.fillMaxSize(),
        signUpUiState = uiState.value,
        onSignUpClick = viewModel::signUp,
        onLoginClick = navigateToLogin,
        onContinueAsGuestClick = { },
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onPasswordVisibilityChange = viewModel::onPasswordVisibilityChange,
        onConfirmPasswordVisibilityChange = viewModel::onConfirmPasswordVisibilityChange,
        snackbarHostState = snackbarHostState
    )

    LaunchedEffect(uiState.value.isAuthenticated) {
        if (uiState.value.isAuthenticated) {
            navigateToHome()
        }
    }

    LaunchedEffect(uiState.value.errorMessage) {
        uiState.value.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
        }
    }
}