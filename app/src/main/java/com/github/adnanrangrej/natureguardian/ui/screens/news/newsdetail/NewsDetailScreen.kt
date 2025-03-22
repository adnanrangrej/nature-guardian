package com.github.adnanrangrej.natureguardian.ui.screens.news.newsdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NewsDetailScreen(
    viewModel: NewsDetailViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState()
    NewsDetailBody(
        modifier = Modifier,
        uiState = uiState.value,
        onRetryClicked = viewModel::loadNewsItem
    )
}