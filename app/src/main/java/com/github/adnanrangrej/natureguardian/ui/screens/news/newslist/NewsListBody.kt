package com.github.adnanrangrej.natureguardian.ui.screens.news.newslist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.ui.components.ErrorScreen

@Composable
fun NewsListBody(
    uiState: NewsListScreenUiState,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,
    fetchMoreNews: () -> Unit
) {
    when (uiState) {
        is NewsListScreenUiState.Success -> {
            NewsItemList(
                newsItems = uiState.newsResponse.items,
                onItemClick = { onItemClick(it.publishedAt) },
                modifier = modifier,
                contentPadding = PaddingValues(8.dp),
                isLoadingMore = uiState.isLoadingMore,
                loadMoreError = uiState.loadMoreError,
                fetchMoreNews = { if (!uiState.isLoadingMore) fetchMoreNews() })
        }

        is NewsListScreenUiState.Error -> {
            ErrorScreen(
                modifier = modifier,
                retryAction = retryAction,
                errorMessage = R.string.error_loading_news
            )
        }

        is NewsListScreenUiState.Loading -> {
            NewsItemListShimmer(modifier = modifier)
        }
    }
}