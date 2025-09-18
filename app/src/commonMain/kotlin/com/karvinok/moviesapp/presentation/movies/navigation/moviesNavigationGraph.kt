package com.karvinok.moviesapp.presentation.movies.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.karvinok.moviesapp.core.navigation.animatedComposable
import com.karvinok.moviesapp.presentation.moviedetails.MovieDetailsScreen
import com.karvinok.moviesapp.presentation.moviedetails.navigation.MovieDetailsNavigationConstants.ARG_MOVIE_ID
import com.karvinok.moviesapp.presentation.moviedetails.navigation.MovieDetailsNavigationConstants.ARG_MOVIE_POSTER_PATH
import com.karvinok.moviesapp.presentation.moviedetails.navigation.MovieDetailsNavigationConstants.ARG_MOVIE_TITLE
import com.karvinok.moviesapp.presentation.moviedetails.navigation.MovieDetailsNavigationConstants.ROUTE_MOVIE_DETAILS_WITH_ARGS
import com.karvinok.moviesapp.presentation.movies.MoviesScreen
import com.karvinok.moviesapp.presentation.movies.navigation.MoviesNavigationConstants.ROUTE_MOVIES
import org.koin.compose.viewmodel.koinViewModel

/**
 * Graph of the movies feature. May contain several feature screens related to that feature and located
 * in the same gradle module with quick navigation.
 * Eg. Movies, MoviesDetails etc..
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.moviesNavGraph(sharedTransitionScope: SharedTransitionScope) {
    animatedComposable(route = ROUTE_MOVIES) {
        MoviesScreen(
            viewModel = koinViewModel(),
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = this@animatedComposable
        )
    }

    animatedComposable(
        route = ROUTE_MOVIE_DETAILS_WITH_ARGS,
        arguments = listOf(
            navArgument(ARG_MOVIE_ID) {
                type = NavType.IntType
            },
            navArgument(ARG_MOVIE_TITLE) {
                type = NavType.StringType
            },
            navArgument(ARG_MOVIE_POSTER_PATH) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) {
        MovieDetailsScreen(
            viewModel = koinViewModel(),
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = this@animatedComposable
        )
    }
}

object MoviesNavigationConstants {
    internal const val ROUTE_MOVIES = "movies"
}
