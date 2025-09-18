package com.karvinok.moviesapp.presentation.movies

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.karvinok.moviesapp.core.base.StateEventViewModel
import com.karvinok.moviesapp.core.base.launch
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class MoviesViewModel(
    private val router: MoviesRouter,
    private val interactor: MoviesInteractor
) : StateEventViewModel<MoviesState, MoviesEvent, MoviesIntent>(
    MoviesState()
) {

    init {
        loadMovies()
        collectFavorites()
        setupSearchFlow()
    }

    companion object {
        const val SEARCH_DEBOUNCE = 300L
    }

    override fun consumeIntent(intent: MoviesIntent) {
        when (intent) {
            MoviesIntent.BackClick -> router.navigateBack()
            MoviesIntent.OnRetryClick -> loadMovies()
            is MoviesIntent.MovieClick -> router.navigateToMovieDetails(intent.movie)
            is MoviesIntent.ToggleFavorite -> toggleFavorite(intent.movieId)
            is MoviesIntent.SwitchMode -> switchMode(intent.mode)
            is MoviesIntent.SearchQueryChanged -> mutateState { copy(searchQuery = intent.query) }
        }
    }

    private fun loadMovies() {
        launch {
            mutateState { copy(isLoading = true, error = null) }

            try {
                val moviesFlow = interactor.subscribeToMovies()
                    .onEach {
                        mutateState { copy(isLoading = false) }
                    }
                    .cachedIn(viewModelScope)

                mutateState {
                    copy(
                        moviesFlow = moviesFlow,
                        error = null
                    )
                }
            } catch (e: Exception) {
                mutateState {
                    copy(
                        isLoading = false,
                        error = MoviesErrorState.UnknownError
                    )
                }

                e.printStackTrace()
            }
        }
    }

    private fun collectFavorites() = launch {
        interactor.getFavoritesFlow().collect { favoriteIds ->
            mutateState { copy(favoriteIds = favoriteIds) }
        }
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchFlow() = launch {
        state.distinctUntilChangedBy { it.searchQuery }
            .debounce(SEARCH_DEBOUNCE)
            .filter { it.searchQuery.isNotBlank() }
            .onEach { loadSearchResults(it.searchQuery) }
            .launchIn(viewModelScope)
    }

    private fun switchMode(mode: MoviesMode) {
        mutateState { copy(currentMode = mode) }

        when (mode) {
            MoviesMode.POPULAR -> loadMovies()
            MoviesMode.SEARCH -> {
                if (currentState.searchQuery.isNotBlank()) {
                    loadSearchResults(currentState.searchQuery)
                } else {
                    // Show empty state for search
                    mutateState {
                        copy(
                            moviesFlow = flowOf(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun loadSearchResults(query: String) {
        if (query.isBlank()) {
            mutateState { copy(moviesFlow = flowOf(), isLoading = false) }
            return
        }

        launch {
            mutateState { copy(isLoading = true, error = null) }

            try {
                val searchFlow = interactor.searchMovies(query)
                    .onEach {
                        mutateState { copy(isLoading = false) }
                    }
                    .cachedIn(viewModelScope)

                mutateState {
                    copy(
                        moviesFlow = searchFlow,
                        error = null
                    )
                }
            } catch (e: Exception) {
                mutateState {
                    copy(
                        isLoading = false,
                        error = MoviesErrorState.UnknownError
                    )
                }
                e.printStackTrace()
            }
        }
    }

    private fun toggleFavorite(movieId: Int) = launch {
        runCatching { interactor.toggleFavorite(movieId) }
    }
}
