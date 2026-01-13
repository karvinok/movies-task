package com.karvinok.moviesapp.domain.movies.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val spokenLanguages: List<SpokenLanguage> = emptyList(),
    val cast: List<CastMember> = emptyList(),
    val images: List<MovieImage> = emptyList(),
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0
)

data class SpokenLanguage(
    val iso6391: String,
    val name: String
)
