package com.karvinok.moviesapp.data.movies.model

import com.karvinok.moviesapp.domain.movies.model.CastMember
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsDTO(
    @SerialName("id") val id: Int,
    @SerialName("cast") val cast: List<CastDTO> = emptyList()
)

@Serializable
data class CastDTO(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("character") val character: String? = null,
    @SerialName("profile_path") val profilePath: String? = null,
    @SerialName("order") val order: Int
)

fun CastDTO.toDomain(): CastMember = CastMember(
    id = id,
    name = name,
    character = character.orEmpty(),
    profilePath = profilePath?.let { "https://image.tmdb.org/t/p/w185$it" }
)
