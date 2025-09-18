package com.karvinok.moviesapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import org.koin.core.component.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

@Composable
fun LoadController(navHostController: NavHostController) =
    DisposableEffect(Unit) {
        val controllerModule = module {
            factory {
                WeakReference(navHostController)
            }
        }

        loadKoinModules(controllerModule)

        onDispose { unloadKoinModules(controllerModule) }
    }

internal class ComposeNavigator : Navigator, KoinComponent {

    private companion object {
        const val RESULT_KEY = "result"
    }

    private val controller: WeakReference<NavHostController>?
        get() = getKoin().getOrNull() // to get latest nav controller weak reference

    override fun back() {

        controller?.get()?.navigateUp()
    }

    override fun isCurrentDestination(route: String): Boolean =
        controller?.get()?.currentDestination?.route == route

    override fun navigateAndClear(route: String) {
        navigateInternal(route) {
            popUpTo(it.graph.id) {
                inclusive = true
            }
        }
    }

    override fun navigateAndClearCurrent(route: String) {
        navigateInternal(route) { controller ->
            controller.currentDestination?.let {
                popUpTo(it.id) {
                    inclusive = true
                }
            }
        }
    }

    override fun navigateTo(route: String) {
        navigateInternal(route = route)
    }

    private fun navigateInternal(
        route: String,
        builder: (NavOptionsBuilder.(controller: NavController) -> Unit) = {}
    ) {
        controller?.get()?.let {
            it.navigate(route = route) {
                builder(this, it)
            }
        }
    }
}
