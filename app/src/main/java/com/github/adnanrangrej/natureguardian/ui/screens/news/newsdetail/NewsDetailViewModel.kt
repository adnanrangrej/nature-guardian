package com.github.adnanrangrej.natureguardian.ui.screens.news.newsdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.domain.usecase.news.GetNewsItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val getNewsItemUseCase: GetNewsItemUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val timestamp: String = checkNotNull(savedStateHandle["timestamp"])
    private val _uiState = MutableStateFlow<NewsDetailsUiState>(NewsDetailsUiState.Loading)
    val uiState: StateFlow<NewsDetailsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadNewsItem()
        }
    }

    fun loadNewsItem() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    getNewsItemUseCase(timestamp)
                }
                _uiState.update {
                    NewsDetailsUiState.Success(result)
                }
            } catch (e: Exception) {
                _uiState.update {
                    NewsDetailsUiState.Error
                }
            }
        }
    }
}