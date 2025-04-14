package com.github.adnanrangrej.natureguardian.ui.screens.news.newsdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.domain.model.news.NewsItem
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianImages
import com.github.adnanrangrej.natureguardian.ui.components.shimmerEffect
import com.github.adnanrangrej.natureguardian.ui.screens.news.newslist.formatDate
import com.github.adnanrangrej.natureguardian.ui.theme.ExtendedTheme
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme

@Composable
fun NewsDetailCard(
    newsItem: NewsItem,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp)
    ) {
        // Title
        item {
            Text(
                text = newsItem.title,
                style = typography.titleLarge
            )
            Spacer(Modifier.height(8.dp))
        }

        // Source
        item {
            Text(
                text = newsItem.sourceName,
                style = typography.labelMedium,
                color = ExtendedTheme.colors.accentLeaf,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(8.dp))
        }

        // Timestamp
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Published At",
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = formatDate(newsItem.publishedAt),
                    style = typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.height(8.dp))
        }

        // Image
        item {
            NatureGuardianImages(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f),
                url = newsItem.image
            )
            Spacer(Modifier.height(8.dp))
        }

        // Description
        item {
            Text(
                text = newsItem.description,
                style = typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))
        }

        // Content
        item {
            Text(
                text = newsItem.content,
                style = typography.bodyMedium
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun NewsDetailCardShimmer(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
    ) {
        // Title
        item {
            Box(
                modifier = Modifier
                    .height(36.dp * 2)
                    .fillMaxWidth()
                    .shimmerEffect()
            )
            Spacer(Modifier.height(8.dp))
        }

        // Source
        item {
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.4f)
                    .shimmerEffect()
            )
            Spacer(Modifier.height(8.dp))
        }

        // Timestamp
        item {
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.3f)
                    .shimmerEffect()
            )
            Spacer(Modifier.height(8.dp))
        }

        // Image
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(300.dp)
                    .shimmerEffect()
            )
            Spacer(Modifier.height(8.dp))
        }

        // Description
        item {
            Box(
                modifier = Modifier
                    .height(20.dp * 5)
                    .fillMaxWidth()
                    .shimmerEffect()
            )
            Spacer(Modifier.height(8.dp))
        }

        // Content
        item {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .shimmerEffect()
            )
            Spacer(Modifier.height(8.dp))
        }
    }

}


@Preview(showBackground = true)
@Composable
fun NewsDetailCardPreview(modifier: Modifier = Modifier) {
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
    NatureGuardianTheme(
        darkTheme = true
    ) {
        NewsDetailCard(
            modifier = Modifier.fillMaxSize(),
            newsItem = demoItem
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsDetailCardShimmerPreview() {
    NatureGuardianTheme {
        NewsDetailCardShimmer(modifier = Modifier.fillMaxSize())
    }
}