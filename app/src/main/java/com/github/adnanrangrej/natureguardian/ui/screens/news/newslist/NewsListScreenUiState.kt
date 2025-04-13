package com.github.adnanrangrej.natureguardian.ui.screens.news.newslist

import com.github.adnanrangrej.natureguardian.domain.model.news.NewsResponse

sealed interface NewsListScreenUiState {
    data class Success(
        val newsResponse: NewsResponse = NewsResponse(items = emptyList(), nextToken = null),
        val isLoadingMore: Boolean = false,
        val loadMoreError: Boolean = false
    ) : NewsListScreenUiState

    object Error : NewsListScreenUiState
    object Loading : NewsListScreenUiState
}