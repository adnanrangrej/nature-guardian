package com.github.adnanrangrej.natureguardian.ui.screens.news.newsdetail

import com.github.adnanrangrej.natureguardian.domain.model.news.NewsItem

sealed interface NewsDetailsUiState {
    data class Success(val newsItem: NewsItem) : NewsDetailsUiState
    object Error : NewsDetailsUiState
    object Loading : NewsDetailsUiState
}