package com.github.adnanrangrej.natureguardian.ui.screens.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginInputForm(
    modifier: Modifier = Modifier,
    loginUiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        EmailInput(
            modifier = Modifier.fillMaxWidth(),
            email = loginUiState.email,
            onEmailChange = onEmailChange,
            emailError = loginUiState.emailError,
            isLoading = loginUiState.isLoading
        )
        PasswordInput(
            modifier = Modifier
                .fillMaxWidth(),
            password = loginUiState.password,
            onPasswordChange = onPasswordChange,
            passwordError = loginUiState.passwordError,
            isPasswordVisible = loginUiState.passwordVisibility,
            isLoading = loginUiState.isLoading,
            onTogglePasswordVisibility = onTogglePasswordVisibility
        )
    }
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String? = null,
    isLoading: Boolean = false
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text(text = "Email") },
        modifier = modifier,
        isError = emailError != null,
        supportingText = if (emailError != null) {
            { Text(text = emailError) }
        } else {
            null
        },
        enabled = !isLoading,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Mail,
                contentDescription = "Email Icon"
            )
        }
    )
}

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordError: String? = null,
    isPasswordVisible: Boolean = false,
    isLoading: Boolean = false,
    onTogglePasswordVisibility: () -> Unit,
    labelText: String = "Password"
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(text = labelText) },
        modifier = modifier,
        isError = passwordError != null,
        supportingText = if (passwordError != null) {
            { Text(text = passwordError) }
        } else {
            null
        },
        enabled = !isLoading,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Password Icon"
            )
        },
        trailingIcon = {
            IconButton(onClick = onTogglePasswordVisibility) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                )
            }
        },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}
