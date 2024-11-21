/**
 * Created by developer on 29-10-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = sqwads_theme_light_primary,
    onPrimary = sqwads_theme_light_onPrimary,
    primaryContainer = sqwads_theme_light_primaryContainer,
    onPrimaryContainer = sqwads_theme_light_onPrimaryContainer,
    secondary = sqwads_theme_light_secondary,
    onSecondary = sqwads_theme_light_onSecondary,
    secondaryContainer = sqwads_theme_light_secondaryContainer,
    onSecondaryContainer = sqwads_theme_light_onSecondaryContainer,
    tertiary = sqwads_theme_light_tertiary,
    onTertiary = sqwads_theme_light_onTertiary,
    tertiaryContainer = sqwads_theme_light_tertiaryContainer,
    onTertiaryContainer = sqwads_theme_light_onTertiaryContainer,
    error = sqwads_theme_light_error,
    errorContainer = sqwads_theme_light_errorContainer,
    onError = sqwads_theme_light_onError,
    onErrorContainer = sqwads_theme_light_onErrorContainer,
    background = sqwads_theme_light_background,
    onBackground = sqwads_theme_light_onBackground,
    surface = sqwads_theme_light_surface,
    onSurface = sqwads_theme_light_onSurface,
    surfaceVariant = sqwads_theme_light_surfaceVariant,
    onSurfaceVariant = sqwads_theme_light_onSurfaceVariant,
    outline = sqwads_theme_light_outline,
    inverseOnSurface = sqwads_theme_light_inverseOnSurface,
    inverseSurface = sqwads_theme_light_inverseSurface,
    inversePrimary = sqwads_theme_light_inversePrimary,
    surfaceTint = sqwads_theme_light_surfaceTint,
    outlineVariant = sqwads_theme_light_outlineVariant,
    scrim = sqwads_theme_light_scrim,
)

private val DarkColors = darkColorScheme(
    primary = sqwads_theme_dark_primary,
    onPrimary = sqwads_theme_dark_onPrimary,
    primaryContainer = sqwads_theme_dark_primaryContainer,
    onPrimaryContainer = sqwads_theme_dark_onPrimaryContainer,
    secondary = sqwads_theme_dark_secondary,
    onSecondary = sqwads_theme_dark_onSecondary,
    secondaryContainer = sqwads_theme_dark_secondaryContainer,
    onSecondaryContainer = sqwads_theme_dark_onSecondaryContainer,
    tertiary = sqwads_theme_dark_tertiary,
    onTertiary = sqwads_theme_dark_onTertiary,
    tertiaryContainer = sqwads_theme_dark_tertiaryContainer,
    onTertiaryContainer = sqwads_theme_dark_onTertiaryContainer,
    error = sqwads_theme_dark_error,
    errorContainer = sqwads_theme_dark_errorContainer,
    onError = sqwads_theme_dark_onError,
    onErrorContainer = sqwads_theme_dark_onErrorContainer,
    background = sqwads_theme_dark_background,
    onBackground = sqwads_theme_dark_onBackground,
    surface = sqwads_theme_dark_surface,
    onSurface = sqwads_theme_dark_onSurface,
    surfaceVariant = sqwads_theme_dark_surfaceVariant,
    onSurfaceVariant = sqwads_theme_dark_onSurfaceVariant,
    outline = sqwads_theme_dark_outline,
    inverseOnSurface = sqwads_theme_dark_inverseOnSurface,
    inverseSurface = sqwads_theme_dark_inverseSurface,
    inversePrimary = sqwads_theme_dark_inversePrimary,
    surfaceTint = sqwads_theme_dark_surfaceTint,
    outlineVariant = sqwads_theme_dark_outlineVariant,
    scrim = sqwads_theme_dark_scrim,
)

@Composable
fun SqwadsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme= when {
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )

}
