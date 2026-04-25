package com.karvinok.moviesapp

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.karvinok.moviesapp.core.navigation.LoadNavigationState
import com.karvinok.moviesapp.presentation.movies.navigation.moviesNavEntries

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RootNavHost(
    modifier: Modifier = Modifier,
    backStack: NavBackStack<NavKey>
) {
    LoadNavigationState(backStack)

    val entryDecorators = listOf<NavEntryDecorator<NavKey>>(
        rememberSaveableStateHolderNavEntryDecorator(),
        rememberViewModelStoreNavEntryDecorator()
    )

    SharedTransitionLayout {
        NavDisplay(
            backStack = backStack,
            modifier = modifier,
            entryDecorators = entryDecorators,
            onBack = {
                if (backStack.size > 1) backStack.removeAt(backStack.lastIndex)
            },
            entryProvider = entryProvider {
                moviesNavEntries(sharedTransitionScope = this@SharedTransitionLayout)
            }
        )
    }
}
