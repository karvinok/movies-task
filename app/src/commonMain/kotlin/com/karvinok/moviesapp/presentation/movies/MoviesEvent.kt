package com.karvinok.moviesapp.presentation.movies

import com.karvinok.moviesapp.core.base.BaseEvent

internal sealed interface MoviesEvent : BaseEvent {
    data class ShowSnackbar(val message: String) : MoviesEvent
}
