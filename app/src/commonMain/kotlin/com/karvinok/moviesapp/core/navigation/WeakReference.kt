package com.karvinok.moviesapp.core.navigation

expect class WeakReference<T : Any>(referred: T) {
    fun get(): T?
}
