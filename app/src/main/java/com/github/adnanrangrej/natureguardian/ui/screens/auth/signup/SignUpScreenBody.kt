package com.github.adnanrangrej.natureguardian.ui.screens.auth.signup

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme


@Composable
fun SignUpScreenBody(
    modifier: Modifier = Modifier,
    onSignUpClick: (email: String, password: String, name: String) -> Unit,
    onLoginClick: () -> Unit,
    onContinueAsGuestClick: () -> Unit = {},
    isLoading: Boolean,
    snackbarHostState: SnackbarHostState
) {
    // State variables
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    // Error messages
    var emailErrorMessage by remember { mutableStateOf<String?>(null) }
    var passwordErrorMessage by remember { mutableStateOf<String?>(null) }
    var confirmPasswordErrorMessage by remember { mutableStateOf<String?>(null) }

    // Validation states
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    var isConfirmPasswordValid by remember { mutableStateOf(false) }
    var isFormValid by remember { mutableStateOf(false) }
    val isNameEntered = name.isNotBlank()

    isFormValid = isNameEntered && isEmailValid && isPasswordValid && isConfirmPasswordValid

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(R.drawable.app_icon),
                contentDescription = "NatureGuardian Logo"
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Name Input
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Name"
                    )
                },
                enabled = !isLoading
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Email Input
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    isEmailValid =
                        email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    emailErrorMessage =
                        if (email.isNotEmpty() && !isEmailValid) "Invalid email format" else null
                },
                label = { Text("Email") },
                isError = emailErrorMessage != null,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Mail,
                        contentDescription = "Email Icon"
                    )
                }
            )
            emailErrorMessage?.let {
                Text(
                    text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(
                        Alignment.Start
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Password Input
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    isPasswordValid = password.isNotEmpty() && password.length >= 6
                    passwordErrorMessage =
                        if (password.isNotEmpty() && !isPasswordValid) "Password must be at least 6 characters" else null

                    if (confirmPassword.isNotEmpty()) {
                        isConfirmPasswordValid = it == confirmPassword
                        confirmPasswordErrorMessage =
                            if (!isConfirmPasswordValid) "Passwords do not match" else null
                    }
                },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                enabled = !isLoading,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon"
                    )
                },
                isError = passwordErrorMessage != null,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )
            passwordErrorMessage?.let {
                Text(
                    text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(
                        Alignment.Start
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password Input
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    isConfirmPasswordValid =
                        confirmPassword.isNotEmpty() && confirmPassword == password
                    confirmPasswordErrorMessage =
                        if (confirmPassword.isNotEmpty() && !isConfirmPasswordValid) "Passwords do not match" else null
                },
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                        Icon(
                            imageVector = if (isConfirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isConfirmPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon"
                    )
                },
                enabled = !isLoading,
                isError = confirmPasswordErrorMessage != null,
                visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )
            confirmPasswordErrorMessage?.let {
                Text(
                    text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(
                        Alignment.Start
                    )
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button
            Button(
                onClick = { onSignUpClick(email, password, name) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && isFormValid
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else {
                        Text("Create Account")
                    }
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            // Login Button
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                Text("Already have an account? ")
                Text(
                    text = "Login",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable(onClick = { if (!isLoading) onLoginClick() })
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Continue as Guest Button
            Text("or")
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { if (!isLoading) onContinueAsGuestClick() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                enabled = !isLoading
            ) {
                Text("Continue as Guest")
            }
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
            onSignUpClick = { _, _, _ -> },
            onLoginClick = {},
            onContinueAsGuestClick = {},
            isLoading = false,
            snackbarHostState = SnackbarHostState()
        )
    }
}