package com.github.adnanrangrej.natureguardian.ui.screens.news.newslist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NewsScreen(
    viewModel: NewsScreenViewModel = hiltViewModel(),
    navigateToNewsDetail: (String) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState()
    NewsBody(
        modifier = Modifier
            .fillMaxSize(),
        uiState = uiState.value,
        onItemClick = navigateToNewsDetail,
        retryAction = viewModel::loadInitialNews,
        fetchMoreNews = viewModel::loadNextPage
    )
}