package com.karvinok.moviesapp.domain.movies.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val spokenLanguages: List<SpokenLanguage> = emptyList()
)

data class SpokenLanguage(
    val iso6391: String,
    val name: String
)
