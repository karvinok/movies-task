package com.karvinok.moviesapp.domain.movies

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.karvinok.moviesapp.data.movies.db.MovieEntity

@OptIn(ExperimentalPagingApi::class)
internal class MoviesRemoteMediator(
    private val moviesRemoteRepository: MoviesRemoteRepository,
    private val moviesLocalRepository: MoviesLocalRepository,
    private val sortBy: String = "popularity.desc"
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val maxPage = state.pages
                        .flatMap { it.data }
                        .maxOfOrNull { it.page } ?: 0
                    maxPage + 1
                }
            }

            if (loadType == LoadType.REFRESH) {
                moviesLocalRepository.deleteAll()
            }

            val networkResponse = moviesRemoteRepository.getPopularMovies(page, sortBy).onFailure {
                it.printStackTrace()
            }

            networkResponse.getOrNull()?.let { response ->
                moviesLocalRepository.insertAll(response.results, page)

                MediatorResult.Success(endOfPaginationReached = !response.hasNextPage)
            } ?: run {
                MediatorResult.Success(endOfPaginationReached = false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }
}