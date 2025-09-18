package com.karvinok.moviesapp.core.design.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

/**
 * Extension object to provide easy access to Material 3 theme values
 * alongside the custom theme system
 */
object Material3 {
    val colorScheme
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colorScheme

    val typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography

    val shapes
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.shapes
}

/**
 * Helper function to get Material 3 colors with fallback to custom theme colors
 */
@Composable
@ReadOnlyComposable
fun primaryColor(): Color = Material3.colorScheme.primary

@Composable
@ReadOnlyComposable
fun backgroundColor(): Color = Material3.colorScheme.background

@Composable
@ReadOnlyComposable
fun surfaceColor(): Color = Material3.colorScheme.surface

@Composable
@ReadOnlyComposable
fun onSurfaceColor(): Color = Material3.colorScheme.onSurface