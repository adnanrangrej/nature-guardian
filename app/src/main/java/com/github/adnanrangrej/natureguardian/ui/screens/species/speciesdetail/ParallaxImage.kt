package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.github.adnanrangrej.natureguardian.ui.components.ConservationStatusChip
import com.github.adnanrangrej.natureguardian.ui.components.NatureGuardianImages
import com.github.adnanrangrej.natureguardian.ui.screens.species.getDrawableResourceId

@Composable
fun ParallaxImage(
    imageHeight: Dp,
    listState: LazyListState,
    imageUrl: String,
    scientificName: String,
    commonName: String,
    redListCategory: String,
    className: String,
    doi: String
) {
    val context = LocalContext.current
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
        IconButton(
            onClick = {
                if (doi.isBlank()) return@IconButton
                val intent = Intent(Intent.ACTION_VIEW, doi.toUri())
                try {
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        context,
                        "Cannot open link: No browser found.",
                        Toast.LENGTH_SHORT
                    ).show()
                    println("Error opening URL with Intent: ${e.localizedMessage}")
                } catch (e: Exception) {
                    Toast.makeText(context, "Error opening link.", Toast.LENGTH_SHORT).show()
                    println("Error opening URL with Intent: ${e.localizedMessage}")
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                contentDescription = "Open in browser"
            )
        }

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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ConservationStatusChip(redlistCategory = redListCategory)
            }
        }
    }
}