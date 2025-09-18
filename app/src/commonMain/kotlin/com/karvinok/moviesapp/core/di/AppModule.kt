@file:OptIn(ExperimentalSerializationApi::class)

package com.karvinok.moviesapp.core.di

import com.karvinok.moviesapp.core.database.databaseModule
import com.karvinok.moviesapp.core.initialization.AppConfig
import com.karvinok.moviesapp.core.navigation.navigationModule
import com.karvinok.moviesapp.core.network.networkModule
import com.karvinok.moviesapp.data.movies.moviesDataModule
import com.karvinok.moviesapp.domain.movies.moviesDomainModule
import com.karvinok.moviesapp.presentation.movies.moviesUiModule
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

private val configModule = module {
    val config = AppConfig()

    single<AppConfig> {
        config
    }

    single {
        Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            explicitNulls = false
            prettyPrint = true
            isLenient = true
        }
    }
}

private val moviesModule = module {
    loadKoinModules(moviesUiModule)
    loadKoinModules(moviesDomainModule)
    loadKoinModules(moviesDataModule)
}

internal val appModule = module {
    loadKoinModules(configModule)
    loadKoinModules(databaseModule)
    loadKoinModules(networkModule)
    loadKoinModules(navigationModule)

    loadKoinModules(moviesModule)
}
