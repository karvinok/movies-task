package com.karvinok.moviesapp.presentation.moviedetails

import androidx.lifecycle.SavedStateHandle
import com.karvinok.moviesapp.core.base.BaseEvent
import com.karvinok.moviesapp.core.base.StateEventViewModel
import com.karvinok.moviesapp.core.base.launch
import com.karvinok.moviesapp.domain.movies.model.Movie
import com.karvinok.moviesapp.presentation.moviedetails.navigation.MovieDetailsNavigationConstants.ARG_MOVIE_ID
import com.karvinok.moviesapp.presentation.moviedetails.navigation.MovieDetailsNavigationConstants.ARG_MOVIE_POSTER_PATH
import com.karvinok.moviesapp.presentation.moviedetails.navigation.MovieDetailsNavigationConstants.ARG_MOVIE_TITLE

internal class MovieDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val interactor: MovieDetailsInteractor,
    private val router: MovieDetailsRouter
) : StateEventViewModel<MovieDetailsState, MovieDetailsEvent, MovieDetailsIntent>(
    initialState = MovieDetailsState(
        movie = Movie(
            id = savedStateHandle.get<Int>(ARG_MOVIE_ID) ?: 0,
            title = savedStateHandle.get<String>(ARG_MOVIE_TITLE).orEmpty(),
            posterPath = savedStateHandle[ARG_MOVIE_POSTER_PATH],
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
