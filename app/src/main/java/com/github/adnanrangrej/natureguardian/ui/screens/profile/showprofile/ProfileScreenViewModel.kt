package com.github.adnanrangrej.natureguardian.ui.screens.profile.showprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.domain.model.profile.ProfileResult
import com.github.adnanrangrej.natureguardian.domain.usecase.auth.LogoutUseCase
import com.github.adnanrangrej.natureguardian.domain.usecase.profile.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel() {
    val uiState: StateFlow<ProfileResult> =
        getUserProfileUseCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileResult.Loading
        )

    fun logout() {
        logoutUseCase()
    }
}