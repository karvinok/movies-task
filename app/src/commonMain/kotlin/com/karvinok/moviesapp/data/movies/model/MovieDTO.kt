package com.karvinok.moviesapp.data.movies.model

import com.karvinok.moviesapp.domain.movies.model.Movie
import com.karvinok.moviesapp.domain.movies.model.MovieDetails
import com.karvinok.moviesapp.domain.movies.model.SpokenLanguage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDTO(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String? = null,
    @SerialName("overview") val overview: String? = null,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("vote_count") val voteCount: Int? = null,
    @SerialName("popularity") val popularity: Double? = null,
    @SerialName("adult") val adult: Boolean? = null,
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("genre_ids") val genreIds: List<Int>? = null,
    @SerialName("spoken_languages") val spokenLanguages: List<SpokenLanguageDTO>? = null
)

@Serializable
data class SpokenLanguageDTO(
    @SerialName("iso_639_1") val iso6391: String,
    @SerialName("name") val name: String
)

fun MovieDTO.toDomain(): Movie = Movie(
    id = id,
    title = title.orEmpty(),
    posterPath = "https://image.tmdb.org/t/p/w342$posterPath",
    popularity = popularity ?: 0.0
)

fun MovieDTO.toMovieDetails(): MovieDetails = MovieDetails(
    id = id,
    title = title.orEmpty(),
    overview = overview.orEmpty(),
    releaseDate = releaseDate.orEmpty(),
    spokenLanguages = spokenLanguages?.map { it.toDomain() } ?: emptyList()
)

fun SpokenLanguageDTO.toDomain(): SpokenLanguage = SpokenLanguage(
    iso6391 = iso6391,
    name = name
)
