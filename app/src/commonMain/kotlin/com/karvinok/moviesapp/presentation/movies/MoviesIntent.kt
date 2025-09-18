package com.karvinok.moviesapp.presentation.movies

import com.karvinok.moviesapp.core.base.BaseIntent
import com.karvinok.moviesapp.domain.movies.model.Movie

internal sealed interface MoviesIntent : BaseIntent {
    data object BackClick : MoviesIntent
    data object OnRetryClick : MoviesIntent
    data class MovieClick(val movie: Movie) : MoviesIntent
    data class ToggleFavorite(val movieId: Int) : MoviesIntent
    data class SwitchMode(val mode: MoviesMode) : MoviesIntent
    data class SearchQueryChanged(val query: String) : MoviesIntent
}
