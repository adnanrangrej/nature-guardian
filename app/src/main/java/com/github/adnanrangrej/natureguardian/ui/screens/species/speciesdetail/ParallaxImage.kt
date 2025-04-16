package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianImages
import com.github.adnanrangrej.natureguardian.ui.screens.species.getDrawableResourceId
import com.github.adnanrangrej.natureguardian.ui.screens.species.getStatusColor

@Composable
fun ParallaxImage(
    imageHeight: Dp,
    listState: LazyListState,
    imageUrl: String,
    scientificName: String,
    commonName: String,
    redListCategory: String,
    className: String
) {
    val statusColor = getStatusColor(redListCategory)
    val placeholder = getDrawableResourceId(className)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeight)
            .graphicsLayer {
                translationY = listState.firstVisibleItemScrollOffset.toFloat() * 0.5f
            }
    ) {
        NatureGuardianImages(
            url = imageUrl,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            placeholder = placeholder
        )

        // Dark gradient scrim for better text visibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        ),
                        startY = 0f,
                        endY = 1000f
                    )
                )
        )

        // Title overlay at the bottom of the hero image
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
        ) {
            Text(
                text = commonName,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            )
            Text(
                text = scientificName,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.9f),
                ),
                modifier = Modifier.padding(top = 4.dp)
            )

            // Status chip
            Row {
                SuggestionChip(
                    onClick = {},
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    statusColor,
                                    CircleShape
                                )
                        )
                    },
                    label = {
                        Text(
                            text = redListCategory,
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = statusColor.copy(alpha = 0.15f),
                        labelColor = statusColor
                    )
                )
            }
        }
    }
}