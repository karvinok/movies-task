package com.karvinok.moviesapp.domain.movies

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext


internal class ToggleFavoriteUseCase(
    private val moviesLocalRepository: MoviesLocalRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(movieId: Int) = withContext(dispatcher) {
        val isFavorited = moviesLocalRepository.isFavorited(movieId)
        if (isFavorited) {
            moviesLocalRepository.removeFavorite(movieId)
        } else {
            moviesLocalRepository.addFavorite(movieId)
        }
    }
}
