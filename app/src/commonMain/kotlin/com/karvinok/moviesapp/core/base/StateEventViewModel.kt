package com.karvinok.moviesapp.core.base

abstract class StateEventViewModel<S : BaseState, E : BaseEvent, I : BaseIntent>(private val initialState: S) :
    BaseViewModel<I>(),
    EventProvider<E> by BaseEventProvider(),
    StateProvider<S> by BaseStateProvider(initialState)
