package com.karvinok.moviesapp.core.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlin.jvm.JvmSuppressWildcards

fun NavGraphBuilder.animatedComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    verticalAppearanceAnimation: Boolean = false,
    enterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        slideIntoContainer(
            towards = if (verticalAppearanceAnimation) {
                SlideDirection.Companion.Up
            } else {
                SlideDirection.Companion.Left
            },
            animationSpec = tween(AnimationConstants.DefaultDurationMillis)
        ) + fadeIn()
    },
    exitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        fadeOut() + slideOutOfContainer(
            towards = if (verticalAppearanceAnimation) {
                SlideDirection.Companion.Down
            } else {
                SlideDirection.Companion.Left
            },
            animationSpec = tween(AnimationConstants.DefaultDurationMillis)
        )
    },
    popEnterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        slideIntoContainer(
            towards = if (verticalAppearanceAnimation) {
                SlideDirection.Companion.Up
            } else {
                SlideDirection.Companion.Right
            },
            animationSpec = tween(AnimationConstants.DefaultDurationMillis)
        ) + fadeIn()
    },
    popExitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        fadeOut() + slideOutOfContainer(
            towards = if (verticalAppearanceAnimation) {
                SlideDirection.Companion.Down
            } else {
                SlideDirection.Companion.Right
            },
            animationSpec = tween(AnimationConstants.DefaultDurationMillis)
        )
    },
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = content
    )
}
