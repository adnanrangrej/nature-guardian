package com.github.adnanrangrej.natureguardian.ui.screens.splashscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.data.local.preferences.PrefsHelper
import com.github.adnanrangrej.natureguardian.domain.usecase.auth.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val prefsHelper: PrefsHelper,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _isPrepopulated = MutableStateFlow<Boolean>(false)
    val isPrepopulated: StateFlow<Boolean> = _isPrepopulated

    private val _isLoggedIn = MutableStateFlow<Boolean>(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    init {
        Log.d("SplashScreenViewModel", "SplashScreenViewModel initialized")
        prepopulateDatabase()
        checkIfUserLoggedIn()
    }

    private fun prepopulateDatabase() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    prefsHelper.checkAndPrepopulateDatabase()
                } catch (e: Exception) {
                    Log.e("SplashScreenViewModel", "Prepopulation failed", e)
                }
                _isPrepopulated.value = prefsHelper.checkIfPrepopulatedDatabase()
                Log.d("SplashScreenViewModel", "Prepopulated: ${_isPrepopulated.value}")
                Log.d("SplashScreenViewModel", "Prepopulated in ViewModel: ${isPrepopulated.value}")
            }
        }
    }

    private fun checkIfUserLoggedIn() {
        val currentUser = getCurrentUserUseCase()
        _isLoggedIn.value = currentUser != null
        Log.d("SplashScreenViewModel", "Is User Logged In: ${_isLoggedIn.value}")
    }
}