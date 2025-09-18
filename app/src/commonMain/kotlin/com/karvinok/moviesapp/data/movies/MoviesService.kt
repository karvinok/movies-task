package com.karvinok.moviesapp.data.movies

import com.karvinok.moviesapp.data.movies.model.MovieDTO
import com.karvinok.moviesapp.data.movies.model.MoviesResponseDTO
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query

interface MoviesService {

    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): Result<MoviesResponseDTO>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Result<MoviesResponseDTO>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): Result<MovieDTO>
}