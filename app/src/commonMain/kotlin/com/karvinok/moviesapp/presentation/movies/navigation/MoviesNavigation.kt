package com.karvinok.moviesapp.presentation.movies.navigation

import androidx.compose.animation.SharedTransitionScope
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import com.karvinok.moviesapp.core.navigation.slideHorizontal
import com.karvinok.moviesapp.presentation.moviedetails.MovieDetailsScreen
import com.karvinok.moviesapp.presentation.moviedetails.navigation.MovieDetailsNavKey
import com.karvinok.moviesapp.presentation.movies.MoviesScreen
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data object MoviesNavKey : NavKey

fun EntryProviderScope<NavKey>.moviesNavEntries(
    sharedTransitionScope: SharedTransitionScope
) {
    entry<MoviesNavKey>(metadata = slideHorizontal()) {
        MoviesScreen(
            viewModel = koinViewModel(),
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = LocalNavAnimatedContentScope.current
        )
    }

    entry<MovieDetailsNavKey>(metadata = slideHorizontal()) { key ->
        MovieDetailsScreen(
            viewModel = koinViewModel { parametersOf(key) },
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = LocalNavAnimatedContentScope.current
        )
    }
}
