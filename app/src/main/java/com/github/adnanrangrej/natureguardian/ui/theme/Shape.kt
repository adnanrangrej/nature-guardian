package com.github.adnanrangrej.natureguardian.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Define corner radii for consistency across the app
val ExtraSmallCornerRadius = 4.dp
val SmallCornerRadius = 8.dp
val MediumCornerRadius = 12.dp
val LargeCornerRadius = 16.dp
val ExtraLargeCornerRadius = 24.dp

// Custom shapes for specific UI components
val SpeciesCardShape = RoundedCornerShape(MediumCornerRadius) // For species detail cards
val NewsCardShape = RoundedCornerShape(SmallCornerRadius) // For news article cards
val ButtonShape = RoundedCornerShape(LargeCornerRadius) // For buttons
val ChatBubbleShape = RoundedCornerShape(
    topStart = MediumCornerRadius,
    topEnd = MediumCornerRadius,
    bottomEnd = MediumCornerRadius,
    bottomStart = 0.dp // Tail effect for chat bubbles
)
val MapOverlayShape =
    RoundedCornerShape(ExtraSmallCornerRadius) // Minimal rounding for map elements

// Define the overall shapes for the Material 3 theme
val NatureGuardianShapes = Shapes(
    extraSmall = RoundedCornerShape(ExtraSmallCornerRadius), // For small components like chips
    small = RoundedCornerShape(SmallCornerRadius), // For buttons, small cards
    medium = RoundedCornerShape(MediumCornerRadius), // For medium cards, dialogs
    large = RoundedCornerShape(LargeCornerRadius), // For large components like bottom sheets
    extraLarge = RoundedCornerShape(ExtraLargeCornerRadius) // For full-screen elements
)