package com.karvinok.moviesapp.presentation.moviedetails

import com.karvinok.moviesapp.core.base.BaseEvent

internal sealed class MovieDetailsEvent : BaseEvent {
    data class ShowLanguagesModal(val languages: List<String>) : MovieDetailsEvent()
}
