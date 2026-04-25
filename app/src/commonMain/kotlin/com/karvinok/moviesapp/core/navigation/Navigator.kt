package com.karvinok.moviesapp.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.Flow

/**
 * Navigation facade over a Nav3 [androidx.navigation3.runtime.NavBackStack].
 * A single [Navigator] is provided by Koin; the current back stack is bound
 * into Koin for the lifetime of the root nav host via [LoadNavigationState].
 */
interface Navigator {

    fun resultFlow(): Flow<Any>

    fun navigate(key: NavKey)

    fun navigateAndClear(key: NavKey)

    fun navigateAndPopTo(key: NavKey, popTo: NavKey, inclusive: Boolean = false)

    fun navigateAndClearCurrent(key: NavKey)

    fun isCurrentDestination(key: NavKey): Boolean

    fun back()

    fun backWithResult(result: Any)
}
