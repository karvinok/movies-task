package com.karvinok.moviesapp.domain.movies

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

interface ToggleFavoriteUseCase {
    suspend operator fun invoke(movieId: Int)
}

internal class ToggleFavoriteUseCaseImpl(
    private val moviesLocalRepository: MoviesLocalRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ToggleFavoriteUseCase {

    override suspend fun invoke(movieId: Int) = withContext(dispatcher) {
        val isFavorited = moviesLocalRepository.isFavorited(movieId)
        if (isFavorited) {
            moviesLocalRepository.removeFavorite(movieId)
        } else {
            moviesLocalRepository.addFavorite(movieId)
        }
    }
}
