package com.karvinok.moviesapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

@Composable
expect fun rememberAppBackStack(vararg elements: NavKey): NavBackStack<NavKey>
