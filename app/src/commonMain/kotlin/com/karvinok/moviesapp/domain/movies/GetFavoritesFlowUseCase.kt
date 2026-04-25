package com.karvinok.moviesapp.domain.movies


internal class GetFavoritesFlowUseCase(private val moviesLocalRepository: MoviesLocalRepository) {
    operator fun invoke() = moviesLocalRepository.getFavoritesFlow()
}
