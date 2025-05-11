package com.github.adnanrangrej.natureguardian.ui.screens.profile.editprofile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    navigateProfile: () -> Unit,
) {
    val uiState = viewModel.uiState.value
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.uploadImage(it)
        }
    }
    EditProfileBody(
        modifier = Modifier,
        uiState = uiState,
        onImageEditClick = { imagePickerLauncher.launch("image/*") },
        onValueChange = viewModel::updateUiState,
        onSaveClick = viewModel::updateProfile
    )

    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            navigateProfile()
        }
    }
}