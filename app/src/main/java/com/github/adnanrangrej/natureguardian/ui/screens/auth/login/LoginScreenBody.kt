package com.github.adnanrangrej.natureguardian.ui.screens.auth.login

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
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
fun LoginScreenBody(
    modifier: Modifier = Modifier,
    onLoginClick: (email: String, password: String) -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit = {},
    onContinueAsGuestClick: () -> Unit = {},
    isLoading: Boolean,
    snackbarHostState: SnackbarHostState
) {
    // State variables for email and password
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Error messages
    var emailErrorMessage by remember { mutableStateOf<String?>(null) }
    var passwordErrorMessage by remember { mutableStateOf<String?>(null) }

    // Validation states
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    var isFormValid by remember { mutableStateOf(false) }

    isFormValid = isEmailValid && isPasswordValid

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
                text = "Login",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

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
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon"
                    )
                },
                isError = passwordErrorMessage != null,
                enabled = !isLoading,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )
            passwordErrorMessage?.let {
                Text(
                    text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(
                        Alignment.Start
                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            // Forget password
            Text(
                text = "Forgot Password?",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(start = 4.dp)
                    .clickable(onClick = { if (!isLoading) onForgotPasswordClick() })
            )


            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button (Enabled only when the email and password are valid)
            Button(
                onClick = {
                    onLoginClick(email, password)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && isFormValid
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Text("Login")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            // Create Account Button
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                Text("Don't have an account? ")
                Text(
                    text = "Sign Up",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable(onClick = { if (!isLoading) onSignUpClick() })
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
private fun LoginScreenBodyPreview() {
    NatureGuardianTheme {
        LoginScreenBody(
            onLoginClick = { email, password -> },
            modifier = Modifier.fillMaxSize(),
            onSignUpClick = {},
            onForgotPasswordClick = {},
            isLoading = false,
            snackbarHostState = SnackbarHostState()
        )
    }
}