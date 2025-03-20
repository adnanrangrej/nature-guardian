package com.github.adnanrangrej.natureguardian.ui.screens.news

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.ui.components.ErrorScreen

@Composable
fun NewsBody(
    uiState: NewsScreenUiState,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,
    fetchMoreNews: () -> Unit
) {
    when (uiState) {
        is NewsScreenUiState.Success -> {
            NewsItemList(
                newsItems = uiState.newsResponse.items,
                onItemClick = { onItemClick(it.publishedAt) },
                modifier = modifier,
                contentPadding = PaddingValues(8.dp),
                isLoadingMore = uiState.isLoadingMore,
                loadMoreError = uiState.loadMoreError,
                fetchMoreNews = { if (!uiState.isLoadingMore) fetchMoreNews() }
            )
        }

        is NewsScreenUiState.Error -> {
            ErrorScreen(modifier = modifier, retryAction = retryAction)
        }

        is NewsScreenUiState.Loading -> {
            NewsItemListShimmer(modifier = modifier)
        }
    }
}