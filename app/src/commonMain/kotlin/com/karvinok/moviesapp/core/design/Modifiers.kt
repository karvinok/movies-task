package com.karvinok.moviesapp.core.design

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.karvinok.moviesapp.core.design.theme.Theme.colors
import com.karvinok.moviesapp.core.design.theme.Theme.shapes

fun Modifier.clickableBounded(
    shape: Shape,
    indicationColor: Color? = null,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) = composed {
    clip(shape = shape).clickable(
        interactionSource = remember { MutableInteractionSource() },
        enabled = isEnabled,
        indication = ripple(
            bounded = true,
            color = indicationColor ?: colors.primary
        ),
        onClick = onClick
    )
}

fun Modifier.shimmer(
    isShowing: Boolean,
    color: Color? = null,
    shape: Shape? = null
): Modifier = composed {
    val lColor = color ?: colors.secondary
    val mShape = shape ?: shapes.small

    val shimmerColors = listOf(
        lColor.copy(0.0f),
        lColor.copy(0.04f),
        lColor.copy(0.7f),
        lColor.copy(0.04f)
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 3000f,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Restart,
            animation = tween(
                durationMillis = 1500,
                easing = FastOutSlowInEasing
            )
        ),
        label = ""
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = 0f, y = 0f),
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )
    clip(mShape).drawWithContent {
        if (isShowing) {
            drawRect(brush = brush)
        } else {
            drawContent()
        }
    }
}
