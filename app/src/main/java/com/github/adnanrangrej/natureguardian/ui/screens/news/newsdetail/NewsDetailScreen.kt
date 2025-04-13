package com.github.adnanrangrej.natureguardian.ui.screens.news.newsdetail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NewsDetailScreen(
    viewModel: NewsDetailViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.value
    NewsDetailBody(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState,
        onRetryClicked = viewModel::loadNewsItem
    )
}