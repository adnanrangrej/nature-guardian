package com.github.adnanrangrej.natureguardian.ui.screens.news.newslist

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.domain.model.news.NewsItem
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme

@Composable
fun NewsItemList(
    newsItems: List<NewsItem>,
    modifier: Modifier = Modifier,
    onItemClick: (NewsItem) -> Unit,
    isLoadingMore: Boolean = false,
    loadMoreError: Boolean = false,
    fetchMoreNews: () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = modifier, contentPadding = contentPadding, state = listState
    ) {
        items(newsItems) { newsItem ->
            NewsItemCard(
                newsItem = newsItem, modifier = Modifier.clickable { onItemClick(newsItem) })
        }
        item {
            if (isLoadingMore) {
                NewsItemCardShimmer()
            }
            if (loadMoreError) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(100.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(
                            text = stringResource(id = R.string.error_loading_more_news),
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Button(
                            onClick = { fetchMoreNews() },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(text = stringResource(id = R.string.retry))
                        }
                    }

                }
            }
        }
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { lastVisibleItemIndex ->
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            Log.d(
                "NewsItemList",
                "lastVisibleItemIndex: $lastVisibleItemIndex, totalItemsCount: $totalItemsCount"
            )
            if (lastVisibleItemIndex != null && lastVisibleItemIndex >= totalItemsCount - 4) {
                Log.d("NewsItemList", "Fetching more news...")
                fetchMoreNews()
            }
        }
    }
}

@Composable
fun NewsItemListShimmer(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(10) {
            NewsItemCardShimmer()
        }
    }
}

@PreviewScreenSizes
@Composable
private fun NewsItemListPreview() {
    val demoItem = NewsItem(
        content = "Vijayawada: Bureau of Energy Efficiency (BEE) has appreciated the efforts of AP government in promoting energy efficiency in a big way. In this regard, BEE secretary Milind Deora highlighted that the southern states of Tamil Nadu and Andhra Pradesh a... [1242 chars]",
        description = "AP and Tamil Nadu lead in establishing energy clubs to raise awareness on energy conservation.",
        id = "News",
        image = "https://www.deccanchronicle.com/h-upload/2025/03/16/1899396-tnaptopinpromotingenergyefficiency.jpg",
        publishedAt = "2025-03-16T19:50:00Z",
        sourceName = "Deccan Chronicle",
        sourceUrl = "https://www.deccanchronicle.com",
        title = "TN, AP Top in Promoting Energy Efficiency",
        url = "https://www.deccanchronicle.com/southern-states/andhra-pradesh/tn-ap-top-in-promoting-energy-efficiency-1867300"
    )

    val demoList = listOf(
        demoItem,
        demoItem,
        demoItem,
        demoItem,
        demoItem,
        demoItem,
        demoItem,
        demoItem,
        demoItem,
        demoItem,
        demoItem,
        demoItem,
        demoItem,
        demoItem,
        demoItem,
        demoItem,
    )
    NatureGuardianTheme {
        NewsItemList(
            newsItems = demoList, onItemClick = {})
    }

}