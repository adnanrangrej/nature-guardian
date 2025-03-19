package com.github.adnanrangrej.natureguardian.ui.theme

import androidx.compose.ui.graphics.Color

// Light Theme Colors
object LightColors {
    val primary = Color(0xFF2E6B34)         // Deep forest green
    val onPrimary = Color(0xFFFFFFFF)
    val primaryContainer = Color(0xFFB8F1B2) // Light mint green
    val onPrimaryContainer = Color(0xFF00210B)
    val secondary = Color(0xFF3B663F)       // Moss green
    val onSecondary = Color(0xFFFFFFFF)
    val secondaryContainer = Color(0xFFCEEBD0) // Pale leaf green
    val onSecondaryContainer = Color(0xFF0C2010)
    val tertiary = Color(0xFF2A6958)        // Pine green
    val onTertiary = Color(0xFFFFFFFF)
    val tertiaryContainer = Color(0xFFAEF0DB) // Light teal
    val onTertiaryContainer = Color(0xFF002117)
    val error = Color(0xFFB91C1C)           // Berry red
    val onError = Color(0xFFFFFFFF)
    val errorContainer = Color(0xFFFFDAD9)
    val onErrorContainer = Color(0xFF410001)
    val background = Color(0xFFF8FFF0)      // Very pale green white
    val onBackground = Color(0xFF191D17)
    val surface = Color(0xFFF8FFF0)
    val onSurface = Color(0xFF191D17)
    val surfaceVariant = Color(0xFFDFE4D7)  // Soft moss
    val onSurfaceVariant = Color(0xFF42493F)
    val outline = Color(0xFF72796D)         // Stone gray
    val outlineVariant = Color(0xFFC2C8BB)
    val scrim = Color(0xFF000000)
    val inverseSurface = Color(0xFF2E322B)
    val inverseOnSurface = Color(0xFFF0F1E5)
    val inversePrimary = Color(0xFF9CD597)  // Soft leaf
    val surfaceDim = Color(0xFFD8DED0)
    val surfaceBright = Color(0xFFF8FFF0)
    val surfaceContainerLowest = Color(0xFFFFFFFA)
    val surfaceContainerLow = Color(0xFFF2F8EA)
    val surfaceContainer = Color(0xFFECF2E4)
    val surfaceContainerHigh = Color(0xFFE6ECDE)
    val surfaceContainerHighest = Color(0xFFE0E6D8)
}

// Dark Theme Colors
object DarkColors {
    val primary = Color(0xFF9CD597)          // Moonlit leaf
    val onPrimary = Color(0xFF00391C)
    val primaryContainer = Color(0xFF185226)  // Deep forest night
    val onPrimaryContainer = Color(0xFFB8F1B2)
    val secondary = Color(0xFFB2CFB5)        // Misty forest
    val onSecondary = Color(0xFF223526)
    val secondaryContainer = Color(0xFF384E3B) // Dark moss
    val onSecondaryContainer = Color(0xFFCEEBD0)
    val tertiary = Color(0xFF92D4BF)         // Night lake
    val onTertiary = Color(0xFF00372C)
    val tertiaryContainer = Color(0xFF1B5042) // Deep pine
    val onTertiaryContainer = Color(0xFFAEF0DB)
    val error = Color(0xFFFFB4AB)           // Faded red
    val onError = Color(0xFF690003)
    val errorContainer = Color(0xFF930006)
    val onErrorContainer = Color(0xFFFFDAD9)
    val background = Color(0xFF101410)      // Night forest
    val onBackground = Color(0xFFE0E6D8)
    val surface = Color(0xFF101410)
    val onSurface = Color(0xFFE0E6D8)
    val surfaceVariant = Color(0xFF42493F)   // Twilight moss
    val onSurfaceVariant = Color(0xFFC2C8BB)
    val outline = Color(0xFF8C9387)          // Forest mist
    val outlineVariant = Color(0xFF42493F)
    val scrim = Color(0xFF000000)
    val inverseSurface = Color(0xFFE0E6D8)
    val inverseOnSurface = Color(0xFF2E322B)
    val inversePrimary = Color(0xFF2E6B34)  // Shadow green
    val surfaceDim = Color(0xFF101410)
    val surfaceBright = Color(0xFF353A33)    // Moonlit clearing
    val surfaceContainerLowest = Color(0xFF0B0F0B)
    val surfaceContainerLow = Color(0xFF191D17)
    val surfaceContainer = Color(0xFF1D211B)  // Forest floor
    val surfaceContainerHigh = Color(0xFF282C25)
    val surfaceContainerHighest = Color(0xFF333730)
}

// Accent Colors
object AccentColors {
    val leaf = Color(0xFF8BC34A)           // Bright leaf
    val moss = Color(0xFF558B2F)           // Deep moss
    val sunlight = Color(0xFFFFD54F)       // Forest sunlight
    val water = Color(0xFF4FC3F7)          // Forest stream
    val berry = Color(0xFFE91E63)          // Forest berry
}

// Extended Color Scheme
data class ExtendedColorScheme(
    val accentLeaf: Color,
    val accentMoss: Color,
    val accentSunlight: Color,
    val accentWater: Color,
    val accentBerry: Color
)

// Light and Dark Extended Colors
val lightExtendedColors = ExtendedColorScheme(
    accentLeaf = AccentColors.leaf,
    accentMoss = AccentColors.moss,
    accentSunlight = AccentColors.sunlight,
    accentWater = AccentColors.water,
    accentBerry = AccentColors.berry
)

val darkExtendedColors = ExtendedColorScheme(
    accentLeaf = AccentColors.leaf.copy(alpha = 0.8f),
    accentMoss = AccentColors.moss.copy(alpha = 0.8f),
    accentSunlight = AccentColors.sunlight.copy(alpha = 0.8f),
    accentWater = AccentColors.water.copy(alpha = 0.8f),
    accentBerry = AccentColors.berry.copy(alpha = 0.8f)
)