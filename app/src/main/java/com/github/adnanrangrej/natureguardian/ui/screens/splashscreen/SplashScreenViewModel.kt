package com.github.adnanrangrej.natureguardian.ui.screens.splashscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.data.local.preferences.PrefsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val prefsHelper: PrefsHelper
) : ViewModel() {

    private val _isPrepopulated = MutableStateFlow<Boolean>(false)
    val isPrepopulated: StateFlow<Boolean> = _isPrepopulated

    init {
        Log.d("SplashScreenViewModel", "SplashScreenViewModel initialized")
        prepopulateDatabase()
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
}