package com.github.adnanrangrej.natureguardian.ui.screens.auth.signup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.adnanrangrej.natureguardian.ui.screens.auth.AuthViewModel

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    SignUpScreenBody(
        modifier = Modifier.fillMaxSize(),
        onSignUpClick = viewModel::signUp,
        onLoginClick = navigateToLogin,
        onContinueAsGuestClick = {},
        isLoading = uiState.value.isLoading
    )

    LaunchedEffect(uiState.value.isAuthenticated) {
        if (uiState.value.isAuthenticated) {
            navigateToHome()
        }
    }

}