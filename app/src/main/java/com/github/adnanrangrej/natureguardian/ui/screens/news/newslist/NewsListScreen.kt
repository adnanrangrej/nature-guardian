package com.github.adnanrangrej.natureguardian.ui.screens.news.newslist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NewsListScreen(
    viewModel: NewsListScreenViewModel = hiltViewModel(),
    navigateToNewsDetail: (String) -> Unit,
) {
    val uiState = viewModel.uiState.value
    NewsListBody(
        modifier = Modifier
            .fillMaxSize(),
        uiState = uiState,
        onItemClick = navigateToNewsDetail,
        retryAction = viewModel::loadInitialNews,
        fetchMoreNews = viewModel::loadNextPage
    )
}