package com.github.adnanrangrej.natureguardian.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Custom shapes for the Nature Guardian conservation tracker app
 * Inspired by natural forest elements with organic curves and corners
 */

// Basic corner shapes
val SmallCornerShape = RoundedCornerShape(4.dp)
val MediumCornerShape = RoundedCornerShape(8.dp)
val LargeCornerShape = RoundedCornerShape(16.dp)

// Organic corner shapes with varying radiuses
val LeafShape = RoundedCornerShape(
    topStart = 24.dp,
    topEnd = 4.dp,
    bottomStart = 16.dp,
    bottomEnd = 16.dp
)

val WaterDropShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp,
    bottomStart = 24.dp,
    bottomEnd = 24.dp
)

val HillShape = RoundedCornerShape(
    topStart = 24.dp,
    topEnd = 24.dp,
    bottomStart = 4.dp,
    bottomEnd = 4.dp
)

val StoneShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 8.dp,
    bottomStart = 4.dp,
    bottomEnd = 12.dp
)

// Material 3 Shape scheme
val AppShapes = Shapes(
    extraSmall = SmallCornerShape,
    small = SmallCornerShape,
    medium = MediumCornerShape,
    large = LargeCornerShape,
    extraLarge = LargeCornerShape
)

// Extension function to apply nature-inspired shapes based on context
object NatureShapes {
    val leaf = LeafShape
    val waterDrop = WaterDropShape
    val hill = HillShape
    val stone = StoneShape

    // For cards with different purposes
    val dataCard = LargeCornerShape
    val actionCard = WaterDropShape
    val infoPanel = LeafShape
    val bottomSheet = HillShape

    // For buttons with different actions
    val primaryButton = RoundedCornerShape(12.dp)
    val secondaryButton = RoundedCornerShape(8.dp)
    val floatingActionButton = RoundedCornerShape(16.dp)

    // For input fields
    val textField = RoundedCornerShape(8.dp)
    val checkbox = RoundedCornerShape(2.dp)
    val chip = RoundedCornerShape(8.dp)
}