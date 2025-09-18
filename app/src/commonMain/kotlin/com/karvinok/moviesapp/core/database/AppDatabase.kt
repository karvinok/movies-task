package com.karvinok.moviesapp.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.karvinok.moviesapp.data.movies.db.FavoritedMovieDao
import com.karvinok.moviesapp.data.movies.db.FavoritedMovieEntity
import com.karvinok.moviesapp.data.movies.db.MovieDao
import com.karvinok.moviesapp.data.movies.db.MovieEntity

@Database(
    entities = [
        MovieEntity::class,
        FavoritedMovieEntity::class,
    ],
    version = 3,
    exportSchema = false
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun favoritedMovieDao(): FavoritedMovieDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
