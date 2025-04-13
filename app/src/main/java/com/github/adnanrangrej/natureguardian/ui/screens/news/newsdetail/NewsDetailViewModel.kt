package com.github.adnanrangrej.natureguardian.ui.screens.news.newsdetail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.domain.usecase.news.GetNewsItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val getNewsItemUseCase: GetNewsItemUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val timestamp: String = checkNotNull(savedStateHandle["timestamp"])
    private val _uiState = mutableStateOf<NewsDetailsUiState>(NewsDetailsUiState.Loading)
    val uiState: State<NewsDetailsUiState> = _uiState

    init {
        loadNewsItem()
    }

    fun loadNewsItem() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    getNewsItemUseCase(timestamp)
                }
                _uiState.value = NewsDetailsUiState.Success(result)
            } catch (e: Exception) {
                Log.e("NewsDetailViewModel", "Error loading news item", e)
                _uiState.value = NewsDetailsUiState.Error
            }
        }
    }
}