package com.github.adnanrangrej.natureguardian.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Extended color scheme with our custom accent colors
data class ExtendedColorScheme(
    val accent1: Color,
    val accent2: Color,
    val accent3: Color,
    val accent4: Color,
    val accent5: Color
)

// CompositionLocal to provide our extended colors
val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColorScheme(
        accent1 = Color.Unspecified,
        accent2 = Color.Unspecified,
        accent3 = Color.Unspecified,
        accent4 = Color.Unspecified,
        accent5 = Color.Unspecified
    )
}

// Light theme extended colors
private val lightExtendedColors = ExtendedColorScheme(
    accent1 = accentLeaf,
    accent2 = accentMoss,
    accent3 = accentSunlight,
    accent4 = accentWater,
    accent5 = accentBerry
)

// Dark theme extended colors
private val darkExtendedColors = ExtendedColorScheme(
    accent1 = accentLeaf.copy(alpha = 0.8f),
    accent2 = accentMoss.copy(alpha = 0.8f),
    accent3 = accentSunlight.copy(alpha = 0.8f),
    accent4 = accentWater.copy(alpha = 0.8f),
    accent5 = accentBerry.copy(alpha = 0.8f)
)

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

/**
 * NatureGuardian app theme
 *
 * @param darkTheme Whether to use dark theme
 * @param dynamicColor Whether to use dynamic color scheme on Android 12+
 * @param highContrast Whether to use high contrast mode (accessibility)
 * @param content The content to apply the theme to
 */
@Composable
fun NatureGuardianTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    highContrast: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkScheme
        else -> lightScheme
    }

    // Apply high contrast adjustments if needed
    val finalColorScheme = if (highContrast) {
        if (darkTheme) {
            colorScheme.copy(
                onBackground = Color.White,
                onSurface = Color.White,
                onPrimary = Color.Black,
                onSecondary = Color.Black,
                onTertiary = Color.Black
            )
        } else {
            colorScheme.copy(
                onBackground = Color.Black,
                onSurface = Color.Black,
                primary = Color(0xFF114D14),
                secondary = Color(0xFF114D14),
                tertiary = Color(0xFF114D14)
            )
        }
    } else {
        colorScheme
    }

    // Choose extended colors based on theme
    val extendedColors = if (darkTheme) darkExtendedColors else lightExtendedColors

    CompositionLocalProvider(
        LocalExtendedColors provides extendedColors
    ) {
        MaterialTheme(
            colorScheme = finalColorScheme,
            typography = AppTypography,
            shapes = AppShapes,
            content = content
        )
    }
}

/**
 * Access the extended color scheme in composable
 */
object ExtendedTheme {
    val colors: ExtendedColorScheme
        @Composable
        get() = LocalExtendedColors.current
}