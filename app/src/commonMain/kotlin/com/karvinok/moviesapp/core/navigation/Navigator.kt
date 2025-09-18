package com.karvinok.moviesapp.core.navigation

/**
 * Navigation controller wrapper
 */
interface Navigator {

    /**
     * Navigates to screen with particular route
     */
    fun navigateTo(route: String)

    /**
     * Navigates to screen with particular route and clear backstack
     */
    fun navigateAndClear(route: String)

    /**
     * Navigates to screen with particular route and popup to current destination
     */
    fun navigateAndClearCurrent(route: String)

    /**
     * Navigates back
     */
    fun back()

    /**
     * Checks if current destination is the same as the given route
     */
    fun isCurrentDestination(route: String): Boolean
}
