package com.karvinok.moviesapp.domain.movies

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val moviesDomainModule = module {
    singleOf(::SubscribePagedMoviesUseCase)
    singleOf(::SearchMoviesUseCase)
    singleOf(::GetMovieDetailsUseCase)
    singleOf(::ToggleFavoriteUseCase)
    singleOf(::GetFavoritesFlowUseCase)
}
