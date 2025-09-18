package com.karvinok.moviesapp.core.initialization

expect class AppConfig() {

    val baseUrl: String

    val apiKey: String

    val token: String
}
