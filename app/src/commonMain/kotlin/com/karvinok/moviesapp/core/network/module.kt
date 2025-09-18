package com.karvinok.moviesapp.core.network

import com.karvinok.moviesapp.core.initialization.AppConfig
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val networkModule = module {
    singleOf(::EngineProvider)

    single {
        val engineProvider = get<EngineProvider>()
        val config = get<AppConfig>()

        val client = HttpClient(engineProvider.provideEngine()) {
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("Authorization", "Bearer ${config.token}")
            }

            install(ContentNegotiation) {
                json(get<Json>())
            }

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }

        Ktorfit.Builder()
            .baseUrl(config.baseUrl)
            .httpClient(client)
            .converterFactories(
                ResultConverterFactory()
            )
            .build()
    }
}
