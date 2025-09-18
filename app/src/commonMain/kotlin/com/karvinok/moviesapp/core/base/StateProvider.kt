package com.karvinok.moviesapp.core.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface StateProvider<S : BaseState> {

    val state: StateFlow<S>

    val currentState: S

    fun dispatchState(state: S)

    fun mutateState(mutator: S.() -> S)
}

class BaseStateProvider<S : BaseState>(initialState: S) : StateProvider<S> {

    private val _state = MutableStateFlow(initialState)

    override val state: StateFlow<S>
        get() = _state.asStateFlow()

    override val currentState: S
        get() = _state.value

    override fun dispatchState(state: S) {
        _state.value = state
    }

    override fun mutateState(mutator: S.() -> S) {
        _state.value = mutator(currentState)
    }
}
