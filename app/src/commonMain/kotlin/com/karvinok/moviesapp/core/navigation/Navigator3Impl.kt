package com.karvinok.moviesapp.core.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.core.component.KoinComponent
import org.koin.core.qualifier.named

internal const val BACK_STACK_QUALIFIER = "navigator3-back-stack"

internal class Navigator3Impl : Navigator, KoinComponent {

    private val results = MutableSharedFlow<Any>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val backStack: NavBackStack<NavKey>?
        get() = getKoin()
            .getOrNull<WeakReference<NavBackStack<NavKey>>>(named(BACK_STACK_QUALIFIER))
            ?.get()

    override fun resultFlow(): Flow<Any> = results

    override fun navigate(key: NavKey) {
        backStack?.add(key)
    }

    override fun navigateAndClear(key: NavKey) {
        backStack?.apply {
            clear()
            add(key)
        }
    }

    override fun navigateAndPopTo(key: NavKey, popTo: NavKey, inclusive: Boolean) {
        val stack = backStack ?: return
        val index = stack.indexOfLast { it == popTo }
        if (index >= 0) {
            val retainUpTo = if (inclusive) index else index + 1
            while (stack.size > retainUpTo) stack.removeAt(stack.lastIndex)
        }
        stack.add(key)
    }

    override fun navigateAndClearCurrent(key: NavKey) {
        backStack?.apply {
            if (isNotEmpty()) removeAt(lastIndex)
            add(key)
        }
    }

    override fun isCurrentDestination(key: NavKey): Boolean =
        backStack?.lastOrNull() == key

    override fun back() {
        backStack?.let { if (it.isNotEmpty()) it.removeAt(it.lastIndex) }
    }

    override fun backWithResult(result: Any) {
        results.tryEmit(result)
        back()
    }
}
