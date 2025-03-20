package com.github.adnanrangrej.natureguardian.ui.screens.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.domain.usecase.news.GetAllNewsNextPageUseCase
import com.github.adnanrangrej.natureguardian.domain.usecase.news.GetAllNewsUseCase
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
class NewsScreenViewModel @Inject constructor(
    private val getAllNewsUseCase: GetAllNewsUseCase,
    private val getAllNewsNextPageUseCase: GetAllNewsNextPageUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<NewsScreenUiState>(NewsScreenUiState.Loading)
    val uiState: StateFlow<NewsScreenUiState> = _uiState.asStateFlow()

    private var nextToken: String? = null
    private var isFetchingMore = false

    init {
        loadInitialNews()
    }

    fun loadInitialNews() {
        _uiState.value = NewsScreenUiState.Loading
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    getAllNewsUseCase()
                }
                nextToken = response.nextToken
                _uiState.update { NewsScreenUiState.Success(response) }
            } catch (e: Exception) {
                _uiState.update { NewsScreenUiState.Error }
            }
        }
    }

    fun loadNextPage() {
        if (nextToken.isNullOrEmpty() || isFetchingMore) return
        isFetchingMore = true

        _uiState.update { currentState ->
            if (currentState is NewsScreenUiState.Success) {
                currentState.copy(isLoadingMore = true, loadMoreError = false)
            } else {
                currentState
            }
        }
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    getAllNewsNextPageUseCase(nextToken!!)
                }
                nextToken = response.nextToken
                val currentNewsResponse =
                    (_uiState.value as? NewsScreenUiState.Success)?.newsResponse
                _uiState.update {
                    NewsScreenUiState.Success(
                        newsResponse = currentNewsResponse?.copy(
                            items = currentNewsResponse.items + response.items
                        ) ?: response,
                        isLoadingMore = false,
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    if (currentState is NewsScreenUiState.Success) {
                        currentState.copy(isLoadingMore = false, loadMoreError = true)
                    } else {
                        currentState
                    }
                }
            } finally {
                isFetchingMore = false
            }
        }
    }
}
