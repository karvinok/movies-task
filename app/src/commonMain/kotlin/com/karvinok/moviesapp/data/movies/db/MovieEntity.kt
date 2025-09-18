package com.karvinok.moviesapp.data.movies.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.karvinok.moviesapp.domain.movies.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterPath: String?,
    val popularity: Double,
    val page: Int
)

fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title,
    posterPath = posterPath,
    popularity = popularity
)

fun Movie.toEntity(page: Int): MovieEntity = MovieEntity(
    id = id,
    title = title,
    posterPath = posterPath,
    popularity = popularity,
    page = page
)
