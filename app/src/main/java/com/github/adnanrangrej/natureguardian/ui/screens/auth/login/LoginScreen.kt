package com.github.adnanrangrej.natureguardian.ui.screens.auth.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel = hiltViewModel(),
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }

    LoginScreenBody(
        modifier = Modifier.fillMaxSize(),
        loginUiState = uiState.value,
        onTogglePasswordVisibility = viewModel::onPasswordVisibilityChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::login,
        onSignUpClick = navigateToSignUp,
        snackbarHostState = snackbarHostState
    )

    LaunchedEffect(uiState.value.isAuthenticated) {
        if (uiState.value.isAuthenticated) {
            navigateToHome()
        }
    }

    LaunchedEffect(uiState.value.errorMessage) {
        uiState.value.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
        }
    }
}