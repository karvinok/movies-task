package com.karvinok.moviesapp.data.movies

import com.karvinok.moviesapp.data.movies.model.toDomain
import com.karvinok.moviesapp.data.movies.model.toMovieDetails
import com.karvinok.moviesapp.domain.movies.MoviesRemoteRepository
import com.karvinok.moviesapp.domain.movies.model.MovieDetails
import com.karvinok.moviesapp.domain.movies.model.MoviesPage
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

internal class MoviesRemoteRepositoryImpl(
    private val moviesService: MoviesService
) : MoviesRemoteRepository {

    override suspend fun getPopularMovies(page: Int, sortBy: String): Result<MoviesPage> {
        return moviesService.getPopularMovies(page, sortBy).mapCatching { it.toDomain() }
    }

    override suspend fun searchMovies(query: String, page: Int): Result<MoviesPage> {
        return moviesService.searchMovies(query, page).mapCatching { it.toDomain() }
    }

    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetails> = coroutineScope {
        try {
            // Fetch all data concurrently
            val detailsDeferred = async { moviesService.getMovieDetails(movieId = movieId) }
            val creditsDeferred = async { moviesService.getMovieCredits(movieId = movieId) }
            val imagesDeferred = async { moviesService.getMovieImages(movieId = movieId) }

            val details = detailsDeferred.await().getOrThrow()
            val credits = creditsDeferred.await().getOrNull()
            val images = imagesDeferred.await().getOrNull()

            // Extract top cast (first 10 members)
            val cast = credits?.cast
                ?.sortedBy { it.order }
                ?.take(10)
                ?.map { it.toDomain() }
                ?: emptyList()

            // Combine backdrops and posters, take first 20
            val movieImages = (images?.backdrops.orEmpty() + images?.posters.orEmpty())
                .take(20)
                .map { it.toDomain() }

            Result.success(details.toMovieDetails(cast = cast, images = movieImages))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}