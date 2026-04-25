package com.karvinok.moviesapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

@Composable
fun LoadNavigationState(backStack: NavBackStack<NavKey>) {
    DisposableEffect(backStack) {
        val stackModule = module {
            factory(qualifier = named(BACK_STACK_QUALIFIER)) { WeakReference(backStack) }
        }
        loadKoinModules(stackModule)
        onDispose { unloadKoinModules(stackModule) }
    }
}
