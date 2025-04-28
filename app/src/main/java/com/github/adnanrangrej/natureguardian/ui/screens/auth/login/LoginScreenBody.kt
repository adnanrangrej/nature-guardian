package com.github.adnanrangrej.natureguardian.ui.screens.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.ui.components.AuthScreenHeader
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme


@Composable
fun LoginScreenBody(
    modifier: Modifier = Modifier,
    loginUiState: LoginUiState,
    onTogglePasswordVisibility: () -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit = {},
    onContinueAsGuestClick: () -> Unit = {},
    snackbarHostState: SnackbarHostState
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AuthScreenHeader(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                headerText = "Login"
            )
            LoginInputForm(
                loginUiState = loginUiState,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onTogglePasswordVisibility = onTogglePasswordVisibility,
            )
            Text(
                text = "Forgot Password?",
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable(onClick = { if (!loginUiState.isLoading) onForgotPasswordClick() })
            )
            // Login Button
            Button(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = loginUiState.isInputValid && !loginUiState.isLoading
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (loginUiState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else {
                        Text("Login")
                    }
                }
            }

            // Footer
            LoginScreenFooter(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onSignUp = onSignUpClick,
                onContinueWithGuest = onContinueAsGuestClick,
                isLoading = loginUiState.isLoading
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenBodyPreview() {
    NatureGuardianTheme {
        LoginScreenBody(
            modifier = Modifier.fillMaxSize(),
            loginUiState = LoginUiState(),
            onLoginClick = { },
            onSignUpClick = { },
            onForgotPasswordClick = { },
            onContinueAsGuestClick = { },
            snackbarHostState = SnackbarHostState()
        )
    }
}