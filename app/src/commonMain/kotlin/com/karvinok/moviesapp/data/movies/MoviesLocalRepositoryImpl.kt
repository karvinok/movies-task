package com.karvinok.moviesapp.data.movies

import androidx.paging.PagingSource
import com.karvinok.moviesapp.data.movies.db.FavoritedMovieDao
import com.karvinok.moviesapp.data.movies.db.FavoritedMovieEntity
import com.karvinok.moviesapp.data.movies.db.MovieDao
import com.karvinok.moviesapp.data.movies.db.MovieEntity
import com.karvinok.moviesapp.data.movies.db.toEntity
import com.karvinok.moviesapp.domain.movies.MoviesLocalRepository
import com.karvinok.moviesapp.domain.movies.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class MoviesLocalRepositoryImpl(
    private val movieDao: MovieDao,
    private val favoritedMovieDao: FavoritedMovieDao
) : MoviesLocalRepository {

    override suspend fun insert(movie: Movie, page: Int) {
        movieDao.insert(movie.toEntity(page))
    }

    override suspend fun insertAll(movies: List<Movie>, page: Int) {
        movieDao.insertAll(movies.map { it.toEntity(page) })
    }

    override suspend fun deleteAll() {
        movieDao.deleteAll()
    }

    override fun providePagingSource(): PagingSource<Int, MovieEntity> {
        return movieDao.pagingSource()
    }

    override suspend fun addFavorite(movieId: Int) {
        return favoritedMovieDao.addFavorite(FavoritedMovieEntity(movieId))
    }

    override suspend fun removeFavorite(movieId: Int) {
        return favoritedMovieDao.removeFavoriteByMovieId(movieId)
    }

    override suspend fun isFavorited(movieId: Int): Boolean {
        return favoritedMovieDao.isFavorited(movieId) > 0
    }

    override suspend fun getAllFavoritedMovieIds(): List<Int> {
        return favoritedMovieDao.getAllFavoritedMovieIds()
    }

    override fun getFavoritesFlow(): Flow<Set<Int>> {
        return favoritedMovieDao.getAllFavoritedMovieIdsFlow().map { it.toSet() }
    }
}
