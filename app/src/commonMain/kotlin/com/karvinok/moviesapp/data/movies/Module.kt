package com.karvinok.moviesapp.data.movies

import com.karvinok.moviesapp.core.network.ktorfitService
import com.karvinok.moviesapp.domain.movies.MoviesLocalRepository
import com.karvinok.moviesapp.domain.movies.MoviesRemoteRepository
import org.koin.dsl.module

val moviesDataModule = module {
    ktorfitService { createMoviesService() }

    single<MoviesRemoteRepository> { MoviesRemoteRepositoryImpl(get()) }
    single<MoviesLocalRepository> { MoviesLocalRepositoryImpl(get(), get()) }
}
