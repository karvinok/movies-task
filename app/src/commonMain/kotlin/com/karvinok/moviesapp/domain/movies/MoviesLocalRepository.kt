package com.karvinok.moviesapp.domain.movies

import androidx.paging.PagingSource
import com.karvinok.moviesapp.data.movies.db.MovieEntity
import com.karvinok.moviesapp.domain.movies.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesLocalRepository {
    suspend fun insert(movie: Movie, page: Int)
    suspend fun insertAll(movies: List<Movie>, page: Int)
    suspend fun deleteAll()
    fun providePagingSource(): PagingSource<Int, MovieEntity>
    suspend fun addFavorite(movieId: Int)
    suspend fun removeFavorite(movieId: Int)
    suspend fun isFavorited(movieId: Int): Boolean
    suspend fun getAllFavoritedMovieIds(): List<Int>
    fun getFavoritesFlow(): Flow<Set<Int>>
}
