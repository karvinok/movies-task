package com.karvinok.moviesapp.core.network

import io.ktor.client.engine.HttpClientEngine

expect class EngineProvider() {

    fun provideEngine(): HttpClientEngine
}
