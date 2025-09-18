package com.karvinok.moviesapp.domain.movies

import com.karvinok.moviesapp.domain.movies.model.MovieDetails
import com.karvinok.moviesapp.domain.movies.model.MovieResponseType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

interface GetMovieDetailsUseCase {
    suspend operator fun invoke(movieId: Int): Result<MovieDetails>
}

internal class GetMovieDetailsUseCaseImpl(
    private val moviesRemoteRepository: MoviesRemoteRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : GetMovieDetailsUseCase {

    override suspend fun invoke(movieId: Int): Result<MovieDetails> = withContext(dispatcher) {
        val generalDetailsResult = async {
            moviesRemoteRepository.getMovieDetails(movieId)
        }.await()

        val releaseDateResult = async {
            moviesRemoteRepository.getMovieDetails(movieId)
        }.await()

        val generalDetails =
            generalDetailsResult.getOrElse { return@withContext Result.failure(it) }
        val releaseDateDetails = releaseDateResult.getOrElse { return@withContext Result.failure(it) }

        return@withContext Result.success(
            generalDetails.copy(
                releaseDate = releaseDateDetails.releaseDate
            )
        )
    }
}
