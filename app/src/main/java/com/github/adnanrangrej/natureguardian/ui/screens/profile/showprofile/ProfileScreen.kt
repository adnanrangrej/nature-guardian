package com.github.adnanrangrej.natureguardian.ui.screens.profile.showprofile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToEditProfile: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    ProfileScreenBody(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState.value,
        onEditProfileClick = navigateToEditProfile,
        onLogoutClick = {
            viewModel.logout()
            navigateToLogin()
        }
    )
}