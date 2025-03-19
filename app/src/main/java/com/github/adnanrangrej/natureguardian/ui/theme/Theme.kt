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

// CompositionLocal to provide our extended colors
val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColorScheme(
        accentLeaf = AccentColors.leaf,
        accentMoss = AccentColors.moss,
        accentSunlight = AccentColors.sunlight,
        accentWater = AccentColors.water,
        accentBerry = AccentColors.berry
    )
}

@Composable
fun NatureGuardianTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    highContrast: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme(
            primary = DarkColors.primary,
            onPrimary = DarkColors.onPrimary,
            primaryContainer = DarkColors.primaryContainer,
            onPrimaryContainer = DarkColors.onPrimaryContainer,
            secondary = DarkColors.secondary,
            onSecondary = DarkColors.onSecondary,
            secondaryContainer = DarkColors.secondaryContainer,
            onSecondaryContainer = DarkColors.onSecondaryContainer,
            tertiary = DarkColors.tertiary,
            onTertiary = DarkColors.onTertiary,
            tertiaryContainer = DarkColors.tertiaryContainer,
            onTertiaryContainer = DarkColors.onTertiaryContainer,
            error = DarkColors.error,
            onError = DarkColors.onError,
            errorContainer = DarkColors.errorContainer,
            onErrorContainer = DarkColors.onErrorContainer,
            background = DarkColors.background,
            onBackground = DarkColors.onBackground,
            surface = DarkColors.surface,
            onSurface = DarkColors.onSurface,
            surfaceVariant = DarkColors.surfaceVariant,
            onSurfaceVariant = DarkColors.onSurfaceVariant,
            outline = DarkColors.outline,
            outlineVariant = DarkColors.outlineVariant,
            scrim = DarkColors.scrim,
            inverseSurface = DarkColors.inverseSurface,
            inverseOnSurface = DarkColors.inverseOnSurface,
            inversePrimary = DarkColors.inversePrimary,
            surfaceDim = DarkColors.surfaceDim,
            surfaceBright = DarkColors.surfaceBright,
            surfaceContainerLowest = DarkColors.surfaceContainerLowest,
            surfaceContainerLow = DarkColors.surfaceContainerLow,
            surfaceContainer = DarkColors.surfaceContainer,
            surfaceContainerHigh = DarkColors.surfaceContainerHigh,
            surfaceContainerHighest = DarkColors.surfaceContainerHighest
        )
        else -> lightColorScheme(
            primary = LightColors.primary,
            onPrimary = LightColors.onPrimary,
            primaryContainer = LightColors.primaryContainer,
            onPrimaryContainer = LightColors.onPrimaryContainer,
            secondary = LightColors.secondary,
            onSecondary = LightColors.onSecondary,
            secondaryContainer = LightColors.secondaryContainer,
            onSecondaryContainer = LightColors.onSecondaryContainer,
            tertiary = LightColors.tertiary,
            onTertiary = LightColors.onTertiary,
            tertiaryContainer = LightColors.tertiaryContainer,
            onTertiaryContainer = LightColors.onTertiaryContainer,
            error = LightColors.error,
            onError = LightColors.onError,
            errorContainer = LightColors.errorContainer,
            onErrorContainer = LightColors.onErrorContainer,
            background = LightColors.background,
            onBackground = LightColors.onBackground,
            surface = LightColors.surface,
            onSurface = LightColors.onSurface,
            surfaceVariant = LightColors.surfaceVariant,
            onSurfaceVariant = LightColors.onSurfaceVariant,
            outline = LightColors.outline,
            outlineVariant = LightColors.outlineVariant,
            scrim = LightColors.scrim,
            inverseSurface = LightColors.inverseSurface,
            inverseOnSurface = LightColors.inverseOnSurface,
            inversePrimary = LightColors.inversePrimary,
            surfaceDim = LightColors.surfaceDim,
            surfaceBright = LightColors.surfaceBright,
            surfaceContainerLowest = LightColors.surfaceContainerLowest,
            surfaceContainerLow = LightColors.surfaceContainerLow,
            surfaceContainer = LightColors.surfaceContainer,
            surfaceContainerHigh = LightColors.surfaceContainerHigh,
            surfaceContainerHighest = LightColors.surfaceContainerHighest
        )
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
                primary = Color(0xFF114D14), // Darker green for better contrast
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
            typography = NatureGuardianTypography,
            shapes = NatureGuardianShapes,
            content = content
        )
    }
}

/**
 * Access the extended color scheme in composables
 */
object ExtendedTheme {
    val colors: ExtendedColorScheme
        @Composable
        get() = LocalExtendedColors.current
}