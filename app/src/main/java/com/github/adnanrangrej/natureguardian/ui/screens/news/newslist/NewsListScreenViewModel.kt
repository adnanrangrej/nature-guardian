package com.github.adnanrangrej.natureguardian.ui.screens.news.newslist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.domain.usecase.news.GetAllNewsNextPageUseCase
import com.github.adnanrangrej.natureguardian.domain.usecase.news.GetAllNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsListScreenViewModel @Inject constructor(
    private val getAllNewsUseCase: GetAllNewsUseCase,
    private val getAllNewsNextPageUseCase: GetAllNewsNextPageUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf<NewsListScreenUiState>(NewsListScreenUiState.Loading)
    val uiState: State<NewsListScreenUiState> = _uiState

    private var nextToken: String? = null
    private var isFetchingMore = false

    init {
        if (_uiState.value !is NewsListScreenUiState.Success) {
            loadInitialNews()
        }
    }

    fun loadInitialNews() {
        _uiState.value = NewsListScreenUiState.Loading
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    getAllNewsUseCase()
                }
                Log.d(
                    "NewsScreenViewModel",
                    "Initial news loaded successfully: ${response.items.size}"
                )
                nextToken = response.nextToken
                _uiState.value = NewsListScreenUiState.Success(response)
            } catch (e: Exception) {
                Log.e("NewsScreenViewModel", "Error loading initial news", e)
                _uiState.value = NewsListScreenUiState.Error
            }
        }
    }

    fun loadNextPage() {
        if (nextToken.isNullOrEmpty() || isFetchingMore) return
        isFetchingMore = true
        Log.d("NewsScreenViewModel", "Loading next page")

        _uiState.value = if (_uiState.value is NewsListScreenUiState.Success) {
            (_uiState.value as NewsListScreenUiState.Success).copy(
                isLoadingMore = true,
                loadMoreError = false
            )
        } else {
            uiState.value
        }
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    getAllNewsNextPageUseCase(nextToken!!)
                }
                nextToken = response.nextToken

                val currentNewsResponse =
                    (uiState.value as NewsListScreenUiState.Success).newsResponse
                _uiState.value = NewsListScreenUiState.Success(
                    newsResponse = currentNewsResponse.copy(
                        items = currentNewsResponse.items + response.items
                    ),
                    isLoadingMore = false
                )
            } catch (e: Exception) {
                Log.e("NewsScreenViewModel", "Error loading next page", e)
                _uiState.value = if (_uiState.value is NewsListScreenUiState.Success) {
                    (_uiState.value as NewsListScreenUiState.Success).copy(
                        isLoadingMore = false,
                        loadMoreError = true
                    )
                } else {
                    uiState.value
                }
            } finally {
                isFetchingMore = false
            }
        }
    }
}
