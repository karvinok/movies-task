package com.karvinok.moviesapp.domain.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.karvinok.moviesapp.domain.movies.model.Movie

internal class SearchMoviesPagingSource(
    private val moviesRemoteRepository: MoviesRemoteRepository,
    private val query: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        if (query.isBlank()) {
            return LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null
            )
        }

        return try {
            val page = params.key ?: 1
            val response = moviesRemoteRepository.searchMovies(query, page)

            response.fold(
                onSuccess = { moviesPage ->
                    LoadResult.Page(
                        data = moviesPage.results,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (moviesPage.results.isEmpty()) null else page + 1
                    )
                },
                onFailure = { exception ->
                    LoadResult.Error(exception)
                }
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}