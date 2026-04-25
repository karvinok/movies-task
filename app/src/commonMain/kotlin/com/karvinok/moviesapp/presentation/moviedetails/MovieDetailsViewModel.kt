package com.karvinok.moviesapp.presentation.moviedetails

import com.karvinok.moviesapp.core.base.StateEventViewModel
import com.karvinok.moviesapp.core.base.launch
import com.karvinok.moviesapp.domain.movies.model.Movie
import com.karvinok.moviesapp.presentation.moviedetails.navigation.MovieDetailsNavKey

internal class MovieDetailsViewModel(
    navKey: MovieDetailsNavKey,
    private val interactor: MovieDetailsInteractor,
    private val router: MovieDetailsRouter
) : StateEventViewModel<MovieDetailsState, MovieDetailsEvent, MovieDetailsIntent>(
    initialState = MovieDetailsState(
        movie = Movie(
            id = navKey.movieId,
            title = navKey.movieTitle,
            posterPath = navKey.moviePosterPath,
            popularity = 0.0
        )
    )
) {

    init {
        loadMovieDetails()
        collectFavorites()
    }

    override fun consumeIntent(intent: MovieDetailsIntent) {
        when (intent) {
            is MovieDetailsIntent.BackClick -> router.navigateBack()
            is MovieDetailsIntent.ToggleFavorite -> toggleFavorite()
            is MovieDetailsIntent.ShowLanguagesModal -> showLanguagesModal()
            is MovieDetailsIntent.DismissLanguagesModal -> dismissLanguagesModal()
            is MovieDetailsIntent.OnRetryClick -> loadMovieDetails()
        }
    }

    private fun loadMovieDetails() = launch {
        mutateState { copy(isLoading = true, error = null) }

        interactor.getMovieDetails(currentState.movie.id)
            .onSuccess { movieDetails ->
                mutateState {
                    copy(
                        overview = movieDetails.overview,
                        releaseDate = movieDetails.releaseDate.take(4),
                        spokenLanguages = movieDetails.spokenLanguages.map { it.name },
                        cast = movieDetails.cast,
                        images = movieDetails.images,
                        voteAverage = movieDetails.voteAverage,
                        voteCount = movieDetails.voteCount,
                        isLoading = false
                    )
                }
            }
            .onFailure { exception ->
                mutateState {
                    copy(
                        isLoading = false,
                        error = MovieDetailsErrorState.NetworkError
                    )
                }
            }
    }

    private fun collectFavorites() = launch {
        interactor.getFavoritesFlow().collect { favoriteIds ->
            mutateState { copy(isFavorite = favoriteIds.contains(movie.id)) }
        }
    }

    private fun toggleFavorite() = launch {
        try {
            interactor.toggleFavorite(currentState.movie.id)
        } catch (e: Exception) {
        }
    }

    private fun showLanguagesModal() {
        if (currentState.spokenLanguages.isNotEmpty()) {
            mutateState { copy(showLanguagesDialog = true) }
        }
    }

    private fun dismissLanguagesModal() {
        mutateState { copy(showLanguagesDialog = false) }
    }
}
