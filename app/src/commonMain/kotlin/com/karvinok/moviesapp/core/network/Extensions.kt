package com.karvinok.moviesapp.core.network

import com.karvinok.moviesapp.data.movies.MoviesService
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module

inline fun <reified S> Module.ktorfitService(crossinline ktorfit: Ktorfit.() -> S): KoinDefinition<S> =
    single {
        ktorfit(get<Ktorfit>())
    }
