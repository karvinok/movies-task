package com.karvinok.moviesapp.core.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.karvinok.moviesapp.core.design.clickableBounded
import com.karvinok.moviesapp.core.design.theme.Dimens
import com.karvinok.moviesapp.core.design.theme.Theme.colors
import com.karvinok.moviesapp.core.design.theme.Theme.shapes
import com.karvinok.moviesapp.core.design.theme.dp24
import com.karvinok.moviesapp.core.design.theme.dp48

@Composable
fun RoundButton(
    painter: Painter,
    modifier: Modifier = Modifier,
    bgColor: Color = colors.bgBlackOp40,
    tint: Color = colors.primary,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(Dimens.dp48())
            .background(
                color = bgColor,
                shape = shapes.full
            )
            .clickableBounded(
                onClick = onClick,
                shape = shapes.full
            )
    ) {
        Icon(
            painter = painter,
            contentDescription = "RoundButton",
            tint = tint,
            modifier = Modifier
                .size(Dimens.dp24())
                .align(Alignment.Center)
        )
    }
}