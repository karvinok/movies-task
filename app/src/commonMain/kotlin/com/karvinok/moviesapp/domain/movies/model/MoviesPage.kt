package com.karvinok.moviesapp.domain.movies.model

data class MoviesPage(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
) {
    val hasNextPage: Boolean
        get() = page < totalPages
}