package com.karvinok.moviesapp.domain.movies

import com.karvinok.moviesapp.domain.movies.model.MovieDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

internal class GetMovieDetailsUseCase(
    private val moviesRemoteRepository: MoviesRemoteRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(movieId: Int): Result<MovieDetails> = withContext(dispatcher) {
        val generalDetailsResult = async {
            moviesRemoteRepository.getMovieDetails(movieId)
        }.await()

        val releaseDateResult = async {
            moviesRemoteRepository.getMovieDetails(movieId)
        }.await()

        val generalDetails =
            generalDetailsResult.getOrElse { return@withContext Result.failure(it) }
        val releaseDateDetails =
            releaseDateResult.getOrElse { return@withContext Result.failure(it) }

        return@withContext Result.success(
            generalDetails.copy(
                releaseDate = releaseDateDetails.releaseDate
            )
        )
    }
}
