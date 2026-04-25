package com.karvinok.moviesapp.presentation.moviedetails.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsNavKey(
    val movieId: Int,
    val movieTitle: String,
    val moviePosterPath: String?
) : NavKey
