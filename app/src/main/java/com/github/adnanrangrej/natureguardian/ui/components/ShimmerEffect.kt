package com.github.adnanrangrej.natureguardian.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.shimmerEffect(
    colors: List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    ),
    durationMillis: Int = 1200
): Modifier = composed {
    val transition = rememberInfiniteTransition()
    val shimmerTranslateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    // Apply the shimmer effect using drawWithContent
    return@composed this.then(
        Modifier.drawWithContent {
            drawContent()
            val gradient = Brush.linearGradient(
                colors = colors,
                start = Offset(shimmerTranslateAnimation.value - 100f, 0f),
                end = Offset(shimmerTranslateAnimation.value, 0f)
            )
            // Draw the shimmer gradient on top of the content
            drawRect(brush = gradient, size = this.size)
        }
    )
}