package com.karvinok.moviesapp.core.initialization

import com.karvinok.moviesapp.BuildConfig

actual class AppConfig {
    actual val baseUrl: String
        get() = BuildConfig.BASE_URL

    actual val apiKey: String
        get() = BuildConfig.API_KEY

    actual val token: String
        get() = BuildConfig.TOKEN
}
