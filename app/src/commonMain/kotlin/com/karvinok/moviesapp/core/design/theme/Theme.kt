package com.karvinok.moviesapp.core.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

object Theme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = ThemeColor.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = ThemeTypography.current

    val spacings: AppSpacings
        @Composable
        @ReadOnlyComposable
        get() = ThemeSpacing.current

    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = ThemeShape.current
}

val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
    error("No Snackbar Host State")
}

// Material 3 Color Schemes
private val DarkColorScheme = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFFE3F2FD),
    onPrimary = androidx.compose.ui.graphics.Color(0xFF0F0F19),
    primaryContainer = androidx.compose.ui.graphics.Color(0xFF1E1E2E),
    onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFFE3F2FD),
    secondary = androidx.compose.ui.graphics.Color(0xFFFFEFF5),
    onSecondary = androidx.compose.ui.graphics.Color(0xFF0F0F19),
    secondaryContainer = androidx.compose.ui.graphics.Color(0xFF2A2A3A),
    onSecondaryContainer = androidx.compose.ui.graphics.Color(0xFFFFEFF5),
    background = androidx.compose.ui.graphics.Color(0xFF0F0F19),
    onBackground = androidx.compose.ui.graphics.Color.White,
    surface = androidx.compose.ui.graphics.Color(0xFF1E1E2E),
    onSurface = androidx.compose.ui.graphics.Color.White,
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFFFFEFF5),
    onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF0F0F19)
)

private val LightColorScheme = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF1976D2),
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = androidx.compose.ui.graphics.Color(0xFFE3F2FD),
    onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFF0D47A1),
    secondary = androidx.compose.ui.graphics.Color(0xFF03DAC6),
    onSecondary = androidx.compose.ui.graphics.Color.Black,
    secondaryContainer = androidx.compose.ui.graphics.Color(0xFFDCF2F2),
    onSecondaryContainer = androidx.compose.ui.graphics.Color(0xFF00695C),
    background = androidx.compose.ui.graphics.Color.White,
    onBackground = androidx.compose.ui.graphics.Color.Black,
    surface = androidx.compose.ui.graphics.Color(0xFFFFFBFE),
    onSurface = androidx.compose.ui.graphics.Color.Black,
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFFFFEFF5),
    onSurfaceVariant = androidx.compose.ui.graphics.Color.Black
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes
    ) {
        CompositionLocalProvider(
            ThemeColor provides provideThemeColors(),
            ThemeShape provides provideThemeShapes(),
            ThemeSpacing provides provideThemeSpacings(),
            ThemeTypography provides provideThemeTypography(),
            LocalSnackbarHostState provides snackbarHostState
        ) {
            content()
        }
    }
}
