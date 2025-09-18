package com.karvinok.moviesapp.core.base

import com.karvinok.moviesapp.core.navigation.Navigator

open class BaseRouter(private val navigator: Navigator) {

    open fun navigateBack() = navigator.back()

    fun isCurrentDestination(route: String) = navigator.isCurrentDestination(route)
}
