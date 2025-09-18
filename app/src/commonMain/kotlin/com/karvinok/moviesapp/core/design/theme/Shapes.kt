package com.karvinok.moviesapp.core.design.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

interface AppShapes {
    val none: RoundedCornerShape
    val tiny: RoundedCornerShape
    val xSmall: RoundedCornerShape
    val small: RoundedCornerShape
    val medium: RoundedCornerShape
    val large: RoundedCornerShape
    val full: RoundedCornerShape
}

internal class AppThemeShapes : AppShapes {
    override val none: RoundedCornerShape = RoundedCornerShape(0)
    override val tiny: RoundedCornerShape = RoundedCornerShape(4.dp)
    override val xSmall: RoundedCornerShape = RoundedCornerShape(8.dp)
    override val small: RoundedCornerShape = RoundedCornerShape(12.dp)
    override val medium: RoundedCornerShape = RoundedCornerShape(16.dp)
    override val large: RoundedCornerShape = RoundedCornerShape(24.dp)
    override val full = RoundedCornerShape(100)
}

internal val ThemeShape = staticCompositionLocalOf { AppThemeShapes() }

@Composable
internal fun provideThemeShapes() = AppThemeShapes()
