package com.karvinok.moviesapp.domain.movies

import com.karvinok.moviesapp.domain.movies.model.MovieDetails
import com.karvinok.moviesapp.domain.movies.model.MovieResponseType
import com.karvinok.moviesapp.domain.movies.model.MoviesPage

interface MoviesRemoteRepository {
    suspend fun getPopularMovies(page: Int, sortBy: String = "popularity.desc"): Result<MoviesPage>
    suspend fun searchMovies(query: String, page: Int): Result<MoviesPage>
    suspend fun getMovieDetails(movieId: Int): Result<MovieDetails>
}