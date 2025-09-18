package com.karvinok.moviesapp.data.movies.model

import com.karvinok.moviesapp.domain.movies.model.MoviesPage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponseDTO(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<MovieDTO>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)

fun MoviesResponseDTO.toDomain(): MoviesPage = MoviesPage(
    page = page,
    results = results.map { it.toDomain() },
    totalPages = totalPages,
    totalResults = totalResults
)