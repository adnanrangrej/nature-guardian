package com.github.adnanrangrej.natureguardian.ui.screens.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.ui.screens.auth.login.EmailInput
import com.github.adnanrangrej.natureguardian.ui.screens.auth.login.PasswordInput

@Composable
fun SignUpInputForm(
    modifier: Modifier = Modifier,
    signUpUiState: SignUpUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onPasswordVisibilityChange: () -> Unit,
    onConfirmPasswordVisibilityChange: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        NameInput(
            modifier = Modifier.fillMaxWidth(),
            name = signUpUiState.name,
            onNameChange = onNameChange,
            isLoading = signUpUiState.isLoading
        )

        EmailInput(
            modifier = Modifier.fillMaxWidth(),
            email = signUpUiState.email,
            onEmailChange = onEmailChange,
            emailError = signUpUiState.emailError,
            isLoading = signUpUiState.isLoading
        )

        PasswordInput(
            modifier = Modifier.fillMaxWidth(),
            password = signUpUiState.password,
            onPasswordChange = onPasswordChange,
            passwordError = signUpUiState.passwordError,
            isPasswordVisible = signUpUiState.passwordVisibility,
            isLoading = signUpUiState.isLoading,
            onTogglePasswordVisibility = onPasswordVisibilityChange,
            labelText = "Password"
        )

        PasswordInput(
            modifier = Modifier.fillMaxWidth(),
            password = signUpUiState.confirmPassword,
            onPasswordChange = onConfirmPasswordChange,
            passwordError = signUpUiState.confirmPasswordError,
            isPasswordVisible = signUpUiState.confirmPasswordVisibility,
            isLoading = signUpUiState.isLoading,
            onTogglePasswordVisibility = onConfirmPasswordVisibilityChange,
            labelText = "Confirm Password"
        )
    }
}


@Composable
fun NameInput(
    modifier: Modifier = Modifier,
    name: String,
    onNameChange: (String) -> Unit,
    isLoading: Boolean
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        modifier = modifier,
        label = { Text("Name") },
        enabled = !isLoading,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Name"
            )
        }
    )
}