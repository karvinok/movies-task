package com.karvinok.moviesapp.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.navigation3.ui.NavDisplay

private const val SlideDurationMillis = 300

fun slideHorizontal(): Map<String, Any> =
    NavDisplay.transitionSpec {
        (slideInHorizontally(tween(SlideDurationMillis)) { it } + fadeIn(tween(SlideDurationMillis)))
            .togetherWith(
                slideOutHorizontally(tween(SlideDurationMillis)) { -it } +
                    fadeOut(tween(SlideDurationMillis))
            )
    } + NavDisplay.popTransitionSpec {
        (slideInHorizontally(tween(SlideDurationMillis)) { -it } + fadeIn(tween(SlideDurationMillis)))
            .togetherWith(
                slideOutHorizontally(tween(SlideDurationMillis)) { it } +
                    fadeOut(tween(SlideDurationMillis))
            )
    } + NavDisplay.predictivePopTransitionSpec { _ ->
        (slideInHorizontally(tween(SlideDurationMillis)) { -it } + fadeIn(tween(SlideDurationMillis)))
            .togetherWith(
                slideOutHorizontally(tween(SlideDurationMillis)) { it } +
                    fadeOut(tween(SlideDurationMillis))
            )
    }
