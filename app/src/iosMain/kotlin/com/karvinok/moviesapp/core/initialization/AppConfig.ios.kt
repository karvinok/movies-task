package com.karvinok.moviesapp.core.initialization

import platform.Foundation.NSBundle

actual class AppConfig {
    actual val baseUrl: String
        get() = NSBundle.mainBundle.objectForInfoDictionaryKey("BASE_URL") as? String
            ?: "https://api.themoviedb.org/3/"

    actual val apiKey: String
        get() = NSBundle.mainBundle.objectForInfoDictionaryKey("TMDB_API_KEY") as? String
            ?: "fallback_api_key"

    actual val token: String
        get() =  NSBundle.mainBundle.objectForInfoDictionaryKey("TMDB_TOKEN") as? String
            ?: "fallback_token"
}
