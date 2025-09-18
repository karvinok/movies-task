package com.karvinok.moviesapp

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.karvinok.moviesapp.presentation.movies.navigation.MoviesNavigationConstants.ROUTE_MOVIES
import com.karvinok.moviesapp.presentation.movies.navigation.moviesNavGraph

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RootNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            modifier = modifier,
            startDestination = ROUTE_MOVIES
        ) {
            moviesNavGraph(sharedTransitionScope = this@SharedTransitionLayout)
        }
    }
}
