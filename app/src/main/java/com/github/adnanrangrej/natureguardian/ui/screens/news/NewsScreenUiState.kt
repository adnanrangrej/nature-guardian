package com.github.adnanrangrej.natureguardian.ui.screens.news

import com.github.adnanrangrej.natureguardian.domain.model.news.NewsResponse

sealed interface NewsScreenUiState {
    data class Success(
        val newsResponse: NewsResponse,
        val isLoadingMore: Boolean = false,
        val loadMoreError: Boolean = false
    ) : NewsScreenUiState

    object Error : NewsScreenUiState
    object Loading : NewsScreenUiState
}