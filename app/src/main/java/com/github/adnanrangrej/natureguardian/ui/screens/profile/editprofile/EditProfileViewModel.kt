package com.github.adnanrangrej.natureguardian.ui.screens.profile.editprofile

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.FileUtils
import com.github.adnanrangrej.natureguardian.domain.model.profile.ProfileResult
import com.github.adnanrangrej.natureguardian.domain.model.profile.User
import com.github.adnanrangrej.natureguardian.domain.usecase.profile.GetUserProfileUseCase
import com.github.adnanrangrej.natureguardian.domain.usecase.profile.UpdateUserProfileUseCase
import com.github.adnanrangrej.natureguardian.domain.usecase.profile.UploadProfileImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val uploadProfileImageUseCase: UploadProfileImageUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = mutableStateOf(EditProfileUiState())
    val uiState: State<EditProfileUiState> = _uiState

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            getUserProfileUseCase().collect { result ->
                when (result) {
                    is ProfileResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoadingProfile = false,
                            errorMessage = result.error
                        )
                    }

                    ProfileResult.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoadingProfile = true
                        )
                    }

                    is ProfileResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoadingProfile = false,
                            userDetail = result.user,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    fun validateInput(userDetail: User = uiState.value.userDetail): Boolean {
        return userDetail.name.isNotEmpty()
    }

    fun updateUiState(userDetail: User) {
        _uiState.value = _uiState.value.copy(
            userDetail = userDetail,
            isEntryValid = validateInput(userDetail)
        )
    }

    fun updateProfile(userDetail: User = uiState.value.userDetail) {
        if (validateInput(userDetail)) {
            _uiState.value = _uiState.value.copy(
                isSavingProfile = true
            )
            viewModelScope.launch {
                updateUserProfileUseCase(userDetail).collect { result ->
                    when (result) {
                        is ProfileResult.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isSavingProfile = false,
                                errorMessage = result.error
                            )
                        }

                        ProfileResult.Loading -> {
                            _uiState.value = _uiState.value.copy(
                                isSavingProfile = true
                            )
                        }

                        is ProfileResult.Success -> {
                            _uiState.value = _uiState.value.copy(
                                isSavingProfile = false,
                                errorMessage = null,
                                successMessage = "Profile updated successfully"
                            )
                        }
                    }
                }
            }
        }
    }

    fun uploadImage(uri: Uri) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isSavingProfile = true
            )
            var tempFile: File? = null

            try {
                tempFile = FileUtils.getFileFromUri(context, uri)

                if (tempFile != null && tempFile.exists()) {
                    val imageUrl = uploadProfileImageUseCase(tempFile)
                    if (imageUrl != null) {
                        _uiState.value = _uiState.value.copy(
                            userDetail = _uiState.value.userDetail.copy(
                                profileImageUrl = imageUrl
                            ),
                            errorMessage = null
                        )
                        updateProfile()
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isSavingProfile = false,
                            errorMessage = "Failed to upload image"
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        isSavingProfile = false,
                        errorMessage = "Failed to create temporary file"
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(
                    isSavingProfile = false,
                    errorMessage = "Failed to upload image"
                )
            } finally {
                tempFile?.delete()
                _uiState.value = _uiState.value.copy(
                    isSavingProfile = false
                )
            }
        }
    }
}