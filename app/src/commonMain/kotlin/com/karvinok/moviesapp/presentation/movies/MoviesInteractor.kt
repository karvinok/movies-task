package com.karvinok.moviesapp.presentation.movies

import androidx.paging.PagingData
import com.karvinok.moviesapp.domain.movies.GetFavoritesFlowUseCase
import com.karvinok.moviesapp.domain.movies.SearchMoviesUseCase
import com.karvinok.moviesapp.domain.movies.SubscribePagedMoviesUseCase
import com.karvinok.moviesapp.domain.movies.ToggleFavoriteUseCase
import com.karvinok.moviesapp.domain.movies.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Contain presentation logic that is not suitable for viewModels
 * Combines useCases into one usage scenario for certain presentation (screen)
 * Must not contain direct links to repositories, DAO's etc
 */
internal class MoviesInteractor(
    private val subscribePagedMoviesUseCase: SubscribePagedMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val getFavoritesFlowUseCase: GetFavoritesFlowUseCase
) {

    suspend fun subscribeToMovies(
        sortBy: String = "popularity.desc"
    ): Flow<PagingData<Movie>> = subscribePagedMoviesUseCase(sortBy)

    suspend fun searchMovies(query: String): Flow<PagingData<Movie>> = searchMoviesUseCase(query)

    suspend fun toggleFavorite(movieId: Int) = toggleFavoriteUseCase(movieId)

    fun getFavoritesFlow(): Flow<Set<Int>> = getFavoritesFlowUseCase()
}
