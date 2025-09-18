package com.karvinok.moviesapp.core.navigation

actual class WeakReference<T : Any> actual constructor(referred: T) {
    private val ref = java.lang.ref.WeakReference(referred)
    
    actual fun get(): T? = ref.get()
}