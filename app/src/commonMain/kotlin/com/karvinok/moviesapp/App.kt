package com.karvinok.moviesapp

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.karvinok.moviesapp.core.design.theme.AppTheme
import com.karvinok.moviesapp.core.design.theme.Theme.colors
import com.karvinok.moviesapp.core.navigation.LoadController

@Composable
fun App() {
    AppTheme {
        val controller = rememberNavController()
        LoadController(navHostController = controller)

        RootNavHost(
            modifier = Modifier.background(colors.bg01),
            navController = controller
        )
    }
}
