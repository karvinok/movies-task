package com.karvinok.moviesapp.presentation.moviedetails

import com.karvinok.moviesapp.domain.movies.GetMovieDetailsUseCase
import com.karvinok.moviesapp.domain.movies.MoviesLocalRepository
import com.karvinok.moviesapp.domain.movies.ToggleFavoriteUseCase
import com.karvinok.moviesapp.domain.movies.model.MovieDetails
import kotlinx.coroutines.flow.Flow

internal class MovieDetailsInteractor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val moviesLocalRepository: MoviesLocalRepository
) {
    suspend fun getMovieDetails(movieId: Int): Result<MovieDetails> {
        return getMovieDetailsUseCase(movieId)
    }

    suspend fun toggleFavorite(movieId: Int) {
        toggleFavoriteUseCase(movieId)
    }

    fun getFavoritesFlow(): Flow<Set<Int>> {
        return moviesLocalRepository.getFavoritesFlow()
    }
}