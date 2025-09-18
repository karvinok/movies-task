@file:OptIn(ExperimentalTime::class)

package com.karvinok.moviesapp.data.movies.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Entity(tableName = "favorited_movies")
data class FavoritedMovieEntity(
    @PrimaryKey
    val movieId: Int,
    val addedAt: Long = Clock.System.now().epochSeconds
)
