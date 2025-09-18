package com.karvinok.moviesapp.presentation.moviedetails

import com.karvinok.moviesapp.core.base.BaseState
import com.karvinok.moviesapp.domain.movies.model.Movie

internal data class MovieDetailsState(
    val movie: Movie,
    val spokenLanguages: List<String> = emptyList(),
    val overview: String? = null,
    val releaseDate: String? = null,
    val genre: String? = null,
    val rating: String? = null,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = true,
    val error: MovieDetailsErrorState? = null,
    val showLanguagesDialog: Boolean = false
) : BaseState

internal sealed class MovieDetailsErrorState {
    data object UnknownError : MovieDetailsErrorState()
    data object NetworkError : MovieDetailsErrorState()
}
