package com.github.adnanrangrej.natureguardian.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.github.adnanrangrej.natureguardian.ui.screens.species.getStatusColor

@Composable
fun ConservationStatusChip(
    modifier: Modifier = Modifier,
    redlistCategory: String
) {
    val statusColor = getStatusColor(redlistCategory)
    SuggestionChip(
        onClick = {},
        label = {
            Text(
                text = redlistCategory,
                style = MaterialTheme.typography.labelMedium
            )
        },
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
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = statusColor.copy(alpha = 0.15f),
            labelColor = statusColor
        ),
        border = null,
        modifier = modifier
    )
}

@Composable
fun ConservationStatusChipShimmer(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f),
                shape = MaterialTheme.shapes.small
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Fake dot
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Fake label shimmer
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(14.dp)
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect()
            )
        }
    }
}
