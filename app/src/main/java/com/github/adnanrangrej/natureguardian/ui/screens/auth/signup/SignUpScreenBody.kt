package com.github.adnanrangrej.natureguardian.ui.screens.auth.signup

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
fun SignUpScreenBody(
    modifier: Modifier = Modifier,
    signUpUiState: SignUpUiState,
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
    onContinueAsGuestClick: () -> Unit = {},
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onPasswordVisibilityChange: () -> Unit,
    onConfirmPasswordVisibilityChange: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AuthScreenHeader(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                headerText = "Signup"
            )
            SignUpInputForm(
                signUpUiState = signUpUiState,
                onNameChange = onNameChange,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onConfirmPasswordChange = onConfirmPasswordChange,
                onPasswordVisibilityChange = onPasswordVisibilityChange,
                onConfirmPasswordVisibilityChange = onConfirmPasswordVisibilityChange
            )
            // Signup Button
            Button(
                onClick = onSignUpClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = signUpUiState.isInputValid && !signUpUiState.isLoading
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (signUpUiState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else {
                        Text("Create Account")
                    }
                }
            }
            // Footer
            SignUpScreenFooter(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onLogInClick = onLoginClick,
                onContinueAsGuestClick = onContinueAsGuestClick,
                isLoading = signUpUiState.isLoading
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
private fun SignUpScreenBodyPreview() {
    NatureGuardianTheme {
        SignUpScreenBody(
            modifier = Modifier.fillMaxSize(),
            signUpUiState = SignUpUiState(),
            onSignUpClick = { },
            onLoginClick = { },
            onContinueAsGuestClick = { },
            onNameChange = { },
            onEmailChange = { },
            onPasswordChange = { },
            onConfirmPasswordChange = { },
            onPasswordVisibilityChange = { },
            onConfirmPasswordVisibilityChange = { },
            snackbarHostState = SnackbarHostState()
        )
    }
}