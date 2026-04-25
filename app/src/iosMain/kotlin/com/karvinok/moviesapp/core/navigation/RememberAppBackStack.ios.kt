package com.karvinok.moviesapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

@Composable
actual fun rememberAppBackStack(vararg elements: NavKey): NavBackStack<NavKey> =
    remember { NavBackStack(*elements) }
