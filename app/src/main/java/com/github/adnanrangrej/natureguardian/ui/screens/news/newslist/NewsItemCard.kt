package com.github.adnanrangrej.natureguardian.ui.screens.news.newslist

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.domain.model.news.NewsItem
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianImages
import com.github.adnanrangrej.natureguardian.ui.components.shimmerEffect
import com.github.adnanrangrej.natureguardian.ui.theme.ExtendedTheme
import com.github.adnanrangrej.natureguardian.ui.theme.NatureGuardianTheme
import com.github.adnanrangrej.natureguardian.ui.theme.NewsCardShape
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun NewsItemCard(
    newsItem: NewsItem, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(), shape = NewsCardShape, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            // Image Thumbnail
            NatureGuardianImages(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                url = newsItem.image
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Text Content
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Title
                Text(
                    text = newsItem.title,
                    style = typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Source and Date Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = newsItem.sourceName,
                        style = typography.labelMedium,
                        color = ExtendedTheme.colors.accentLeaf,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Published At",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = formatDate(newsItem.publishedAt),
                        style = typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Optional Description
                if (newsItem.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = newsItem.description,
                        style = typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun NewsItemCardShimmer(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(), shape = NewsCardShape, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            // Image Thumbnail
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(NewsCardShape)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Text Content
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Title
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .fillMaxWidth()
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Source and Date Row
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxSize(0.7f)
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Description
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .fillMaxSize()
                        .shimmerEffect()
                )
            }
        }
    }
}


@PreviewScreenSizes
@Composable
private fun NewsItemCardShimmerPreview() {
    NatureGuardianTheme {
        NewsItemCardShimmer()
    }
}


@PreviewScreenSizes
@Composable
private fun NewsItemCarPreview() {
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
        NewsItemCard(
            newsItem = demoItem
        )
    }
}

fun formatDate(dateString: String): String = try {
    val offsetDateTime = OffsetDateTime.parse(dateString)
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault())
    offsetDateTime.format(formatter)
} catch (e: Exception) {
    Log.e("formatDate", "formatDate: $e")
    dateString
}