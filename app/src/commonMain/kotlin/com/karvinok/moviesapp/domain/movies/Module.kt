package com.karvinok.moviesapp.domain.movies

import org.koin.dsl.module

val moviesDomainModule = module {
    single<SubscribePagedMoviesUseCase> { SubscribePagedMoviesUseCaseImpl(get(), get()) }
    single<SearchMoviesUseCase> { SearchMoviesUseCaseImpl(get()) }
    single<GetMovieDetailsUseCase> { GetMovieDetailsUseCaseImpl(get()) }
    single<ToggleFavoriteUseCase> { ToggleFavoriteUseCaseImpl(get()) }
}
