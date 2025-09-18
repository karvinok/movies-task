package com.karvinok.moviesapp.presentation.movies

import com.karvinok.moviesapp.presentation.moviedetails.MovieDetailsInteractor
import com.karvinok.moviesapp.presentation.moviedetails.MovieDetailsRouter
import com.karvinok.moviesapp.presentation.moviedetails.MovieDetailsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val moviesUiModule = module {
    viewModelOf(::MoviesViewModel)
    single { MoviesInteractor(get(), get(), get(), get()) }
    singleOf(::MoviesRouter)

    viewModelOf(::MovieDetailsViewModel)
    single { MovieDetailsInteractor(get(), get(), get()) }
    singleOf(::MovieDetailsRouter)
}
