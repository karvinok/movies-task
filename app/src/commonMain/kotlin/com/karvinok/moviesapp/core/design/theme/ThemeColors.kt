package com.karvinok.moviesapp.core.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

interface AppColors {
    val bg01: Color get() = Color.Unspecified
    val bg02: Color get() = Color.Unspecified
    val text01: Color get() = Color.Unspecified
    val text02: Color get() = Color.Unspecified
    val primary: Color get() = Color.Unspecified
    val secondary: Color get() = Color.Unspecified
    val surface01: Color get() = Color.Unspecified
    val surface02: Color get() = Color.Unspecified
    val bgBlackOp40: Color get() = Color.Unspecified
    val interactive01Disabled: Color get() = Color.Unspecified
}

internal val ThemeColor = staticCompositionLocalOf<AppColors> { object : AppColors {} }

@Composable
internal fun provideThemeColors(): AppColors = object : AppColors {
    override val bg01 = Color(red = 15, green = 15, blue = 25)
    override val bg02 = Color.White
    override val text01 = Color.White
    override val text02 = Color.White.copy(alpha = 0.6f)
    override val primary = Color.White
    override val secondary = Color.Gray
    override val surface01 = Color(0xFFFFEFF5)
    override val surface02 = Color(0xFFDCF2F2)
    override val bgBlackOp40 = Color.Black.copy(alpha = 0.4f)
    override val interactive01Disabled = Color.White.copy(alpha = 0.5f)
}
