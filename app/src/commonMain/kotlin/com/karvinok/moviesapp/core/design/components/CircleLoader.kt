package com.karvinok.moviesapp.core.design.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.karvinok.moviesapp.core.design.theme.AppTheme
import com.karvinok.moviesapp.core.design.theme.Theme
import com.karvinok.moviesapp.core.design.theme.Theme.colors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CircleLoader(
    modifier: Modifier = Modifier,
    indicatorSize: Dp = 20.dp,
    colors: List<Color> = listOf(
        Color.Transparent,
        Theme.colors.primary.copy(alpha = 0.3f)
    ),
    animationDuration: Int = 1000
) {
    val strokeWidth = indicatorSize / 6
    val infiniteTransition = rememberInfiniteTransition(label = "InfiniteTransition")
    val rotateAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDuration,
                easing = LinearEasing
            )
        ),
        label = "RotateAnimation"
    )
    Canvas(
        modifier = modifier
            .size(indicatorSize)
            .rotate(degrees = rotateAnimation)
    ) {
        drawArc(
            brush = Brush.sweepGradient(colors),
            startAngle = 15f,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            ),
            size = Size(size.height, size.height)
        )
    }
}

@Preview
@Composable
private fun CircleLoaderPreview() {
    AppTheme {
        CircleLoader(
            colors = listOf(
                Color.Transparent,
                colors.primary
            )
        )
    }
}
