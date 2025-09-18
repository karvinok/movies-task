package com.karvinok.moviesapp.data.movies.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritedMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favoriteMovie: FavoritedMovieEntity)

    @Delete
    suspend fun removeFavorite(favoriteMovie: FavoritedMovieEntity)

    @Query("DELETE FROM favorited_movies WHERE movieId = :movieId")
    suspend fun removeFavoriteByMovieId(movieId: Int)

    @Query("SELECT COUNT(*) FROM favorited_movies WHERE movieId = :movieId")
    suspend fun isFavorited(movieId: Int): Int

    @Query("SELECT movieId FROM favorited_movies")
    suspend fun getAllFavoritedMovieIds(): List<Int>

    @Query("SELECT movieId FROM favorited_movies")
    fun getAllFavoritedMovieIdsFlow(): Flow<List<Int>>
}
