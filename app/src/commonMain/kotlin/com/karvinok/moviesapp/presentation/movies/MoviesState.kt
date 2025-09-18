package com.karvinok.moviesapp.presentation.movies

import androidx.paging.PagingData
import com.karvinok.moviesapp.core.base.BaseState
import com.karvinok.moviesapp.domain.movies.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal data class MoviesState(
    val moviesFlow: Flow<PagingData<Movie>> = flowOf(),
    val favoriteIds: Set<Int> = emptySet(),
    val isOnlyFavoritesFilterEnabled: Boolean = false,
    val currentMode: MoviesMode = MoviesMode.POPULAR,
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: MoviesErrorState? = null
) : BaseState

internal enum class MoviesMode {
    POPULAR,
    SEARCH
}

internal sealed class MoviesErrorState {
    data object UnknownError : MoviesErrorState()
}
