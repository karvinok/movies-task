package com.karvinok.moviesapp

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.karvinok.moviesapp.core.design.theme.AppTheme
import com.karvinok.moviesapp.core.design.theme.Theme.colors
import com.karvinok.moviesapp.core.navigation.rememberAppBackStack
import com.karvinok.moviesapp.presentation.movies.navigation.MoviesNavKey

@Composable
fun App() {
    AppTheme {
        val backStack = rememberAppBackStack(MoviesNavKey)

        RootNavHost(
            modifier = Modifier.background(colors.bg01),
            backStack = backStack
        )
    }
}
