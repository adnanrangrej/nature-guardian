package com.github.adnanrangrej.natureguardian.ui.screens.news.newsdetail

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.github.adnanrangrej.natureguardian.R
import com.github.adnanrangrej.natureguardian.domain.model.news.NewsItem
import com.github.adnanrangrej.natureguardian.ui.components.ErrorScreen
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme

@Composable
fun NewsDetailBody(
    modifier: Modifier = Modifier,
    uiState: NewsDetailsUiState,
    onRetryClicked: () -> Unit
) {
    val context = LocalContext.current

    when (uiState) {
        is NewsDetailsUiState.Error -> {
            ErrorScreen(modifier = modifier, retryAction = onRetryClicked)
        }

        is NewsDetailsUiState.Loading -> {
            NewsDetailCardShimmer(modifier = modifier)
        }

        is NewsDetailsUiState.Success -> {
            Column(modifier = modifier) {
                NewsDetailCard(
                    newsItem = uiState.newsItem,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, uiState.newsItem.url.toUri())
                            context.startActivity(intent)
                        }
                    ) {
                        Text(text = stringResource(R.string.click_here_to_read_full_news))
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun NewsDetailBodyPreview() {
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
    NatureGuardianTheme {
        NewsDetailBody(
            uiState = NewsDetailsUiState.Success(demoItem),
            onRetryClicked = {}
        )
    }
}