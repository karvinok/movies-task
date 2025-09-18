package com.karvinok.moviesapp.data.movies

import com.karvinok.moviesapp.data.movies.model.toDomain
import com.karvinok.moviesapp.data.movies.model.toMovieDetails
import com.karvinok.moviesapp.domain.movies.MoviesRemoteRepository
import com.karvinok.moviesapp.domain.movies.model.MovieDetails
import com.karvinok.moviesapp.domain.movies.model.MovieResponseType
import com.karvinok.moviesapp.domain.movies.model.MoviesPage
import kotlinx.coroutines.delay

internal class MoviesRemoteRepositoryImpl(
    private val moviesService: MoviesService
) : MoviesRemoteRepository {

    override suspend fun getPopularMovies(page: Int, sortBy: String): Result<MoviesPage> {
        return moviesService.getPopularMovies(page, sortBy).mapCatching { it.toDomain() }
    }

    override suspend fun searchMovies(query: String, page: Int): Result<MoviesPage> {
        return moviesService.searchMovies(query, page).mapCatching { it.toDomain() }
    }

    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetails> {
        return moviesService.getMovieDetails(movieId = movieId).mapCatching { it.toMovieDetails() }
    }
}