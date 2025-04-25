package com.github.adnanrangrej.natureguardian.ui.screens.auth.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.adnanrangrej.natureguardian.ui.screens.auth.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()

    LoginScreenBody(
        modifier = Modifier.fillMaxSize(),
        onLoginClick = viewModel::login,
        onSignUpClick = navigateToSignUp,
        onForgotPasswordClick = {},
        onContinueAsGuestClick = {},
        isLoading = uiState.value.isLoading
    )

    LaunchedEffect(uiState.value.isAuthenticated) {
        if (uiState.value.isAuthenticated) {
            navigateToHome()
        }
    }
}