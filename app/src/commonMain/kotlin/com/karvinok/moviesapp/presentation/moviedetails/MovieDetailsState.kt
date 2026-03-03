package com.karvinok.moviesapp.presentation.moviedetails

import com.karvinok.moviesapp.core.base.BaseState
import com.karvinok.moviesapp.domain.movies.model.CastMember
import com.karvinok.moviesapp.domain.movies.model.Movie
import com.karvinok.moviesapp.domain.movies.model.MovieImage

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
    val showLanguagesDialog: Boolean = false,
    val cast: List<CastMember> = emptyList(),
    val images: List<MovieImage> = emptyList(),
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0
) : BaseState

internal sealed class MovieDetailsErrorState {
    data object UnknownError : MovieDetailsErrorState()
    data object NetworkError : MovieDetailsErrorState()
}
