package com.karvinok.moviesapp.presentation.moviedetails

import com.karvinok.moviesapp.core.base.BaseIntent

internal sealed interface MovieDetailsIntent : BaseIntent {
    data object BackClick : MovieDetailsIntent
    data object ToggleFavorite : MovieDetailsIntent
    data object ShowLanguagesModal : MovieDetailsIntent
    data object DismissLanguagesModal : MovieDetailsIntent
    data object OnRetryClick : MovieDetailsIntent
}
