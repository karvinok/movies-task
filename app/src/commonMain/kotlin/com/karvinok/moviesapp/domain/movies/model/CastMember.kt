package com.karvinok.moviesapp.domain.movies.model

data class CastMember(
    val id: Int,
    val name: String,
    val character: String,
    val profilePath: String?
)
