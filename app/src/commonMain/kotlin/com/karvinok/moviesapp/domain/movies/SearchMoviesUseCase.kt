package com.karvinok.moviesapp.domain.movies

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.karvinok.moviesapp.domain.movies.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface SearchMoviesUseCase {
    suspend operator fun invoke(query: String): Flow<PagingData<Movie>>
}

internal class SearchMoviesUseCaseImpl(
    private val moviesRemoteRepository: MoviesRemoteRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : SearchMoviesUseCase {

    override suspend fun invoke(query: String) = withContext(dispatcher) {
        Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 10
            )
        ) {
            SearchMoviesPagingSource(moviesRemoteRepository, query)
        }.flow.map { pagingData ->
            pagingData.map { movie ->
                movie
            }
        }
    }
}
