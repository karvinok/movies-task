package com.karvinok.moviesapp.core.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

interface AppSpacings {
    val s2: Dp
    val s4: Dp
    val s8: Dp
    val s12: Dp
    val s16: Dp
    val s20: Dp
    val s24: Dp
    val s32: Dp
    val s40: Dp
    val s48: Dp
    val s56: Dp
    val s64: Dp
    val s72: Dp
    val s80: Dp
}

class ThemeSpacings : AppSpacings {
    override val s2: Dp = 2.dp
    override val s4: Dp = 4.dp
    override val s8: Dp = 8.dp
    override val s12: Dp = 12.dp
    override val s16: Dp = 16.dp
    override val s20: Dp = 20.dp
    override val s24: Dp = 24.dp
    override val s32: Dp = 32.dp
    override val s40: Dp = 40.dp
    override val s48: Dp = 48.dp
    override val s56: Dp = 56.dp
    override val s64: Dp = 64.dp
    override val s72: Dp = 72.dp
    override val s80: Dp = 80.dp
}

internal val ThemeSpacing = staticCompositionLocalOf { ThemeSpacings() }

@Composable
internal fun provideThemeSpacings() = ThemeSpacings()
