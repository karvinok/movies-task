package com.karvinok.moviesapp.core.base

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface EventProvider<E : BaseEvent> {

    val events: SharedFlow<E>

    suspend fun dispatchEvent(event: E)

    suspend fun dispatchEventDelayed(event: E, delay: Long)
}

class BaseEventProvider<E : BaseEvent> : EventProvider<E> {

    private val _events = MutableSharedFlow<E>()

    override val events: SharedFlow<E>
        get() = _events.asSharedFlow()

    override suspend fun dispatchEvent(event: E) {
        _events.emit(event)
    }

    override suspend fun dispatchEventDelayed(event: E, delay: Long) {
        delay(delay)
        _events.emit(event)
    }
}
