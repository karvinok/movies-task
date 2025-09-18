@file:OptIn(ExperimentalNativeApi::class)

package com.karvinok.moviesapp.core.navigation

import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.ref.WeakReference as NativeWeakReference

actual class WeakReference<T : Any> actual constructor(referred: T) {
    private val ref = NativeWeakReference(referred)

    actual fun get(): T? = ref.get()
}
