package com.karvinok.moviesapp.core.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

interface AppTypography {
    val body01: TextStyle
    val body01Highlight: TextStyle
    val body02: TextStyle
    val headingMedium: TextStyle
}

internal class AppThemeTypography : AppTypography {
    override val body01 = TextStyle.Default
    override val body01Highlight = TextStyle(fontWeight = FontWeight.SemiBold)
    override val body02: TextStyle = TextStyle(fontSize = 12.sp)
    override val headingMedium = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
}

internal val ThemeTypography = staticCompositionLocalOf { AppThemeTypography() }

@Composable
internal fun provideThemeTypography() = AppThemeTypography()
