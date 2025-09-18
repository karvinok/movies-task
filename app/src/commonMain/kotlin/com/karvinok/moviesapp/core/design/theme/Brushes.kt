package com.karvinok.moviesapp.core.design.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val brushGradient1 = Brush.linearGradient(
    colorStops = arrayOf(
        0f to Color(0xFFE6F2FE),
        0.6f to Color(0xFFE8F4FF),
        1f to Color(0xFFEBEAFC)
    )
)

val brushGradient2 = Brush.linearGradient(
    colorStops = arrayOf(
        0f to Color(0xFFE8F2FE),
        1f to Color(0xFFFCE9E9)
    )
)

val brushGradient3 = Brush.linearGradient(
    colorStops = arrayOf(
        0f to Color(0xFFECFADE),
        1f to Color(0xFFD0F5E9)
    )
)
