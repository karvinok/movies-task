package com.karvinok.moviesapp.domain.movies.model

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val popularity: Double,
    val isFavorited: Boolean = false,
)