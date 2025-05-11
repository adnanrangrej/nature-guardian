package com.github.adnanrangrej.natureguardian.ui.screens.profile.editprofile

import com.github.adnanrangrej.natureguardian.domain.model.profile.User

data class EditProfileUiState(
    val userDetail: User = User(),
    val isEntryValid: Boolean = false,
    val isLoadingProfile: Boolean = false,
    val isSavingProfile: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)