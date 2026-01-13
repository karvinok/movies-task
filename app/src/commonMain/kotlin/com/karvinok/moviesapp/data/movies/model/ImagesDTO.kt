package com.karvinok.moviesapp.data.movies.model

import com.karvinok.moviesapp.domain.movies.model.MovieImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesDTO(
    @SerialName("id") val id: Int,
    @SerialName("backdrops") val backdrops: List<ImageDTO> = emptyList(),
    @SerialName("posters") val posters: List<ImageDTO> = emptyList()
)

@Serializable
data class ImageDTO(
    @SerialName("file_path") val filePath: String,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
    @SerialName("vote_average") val voteAverage: Double? = null
)

fun ImageDTO.toDomain(): MovieImage = MovieImage(
    filePath = "https://image.tmdb.org/t/p/w500$filePath",
    width = width,
    height = height
)
