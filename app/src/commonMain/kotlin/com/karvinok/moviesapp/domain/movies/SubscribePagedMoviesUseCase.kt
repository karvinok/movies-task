package com.karvinok.moviesapp.domain.movies

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.karvinok.moviesapp.data.movies.db.toDomain
import com.karvinok.moviesapp.domain.movies.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface SubscribePagedMoviesUseCase {
    suspend operator fun invoke(sortBy: String = "popularity.desc"): Flow<PagingData<Movie>>
}

internal class SubscribePagedMoviesUseCaseImpl(
    private val moviesLocalRepository: MoviesLocalRepository,
    private val moviesRemoteRepository: MoviesRemoteRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : SubscribePagedMoviesUseCase {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun invoke(sortBy: String) = withContext(dispatcher) {
        Pager(
            config = defaultPagingConfig(20),
            remoteMediator = MoviesRemoteMediator(
                moviesRemoteRepository = moviesRemoteRepository,
                moviesLocalRepository = moviesLocalRepository,
                sortBy = sortBy
            )
        ) {
            moviesLocalRepository.providePagingSource()
        }.flow.map { pagingData ->
            pagingData.map { movieEntity ->
                movieEntity.toDomain()
            }
        }
    }

    private fun defaultPagingConfig(pageSize: Int): PagingConfig {
        return PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize * 2,
            prefetchDistance = pageSize
        )
    }
}
