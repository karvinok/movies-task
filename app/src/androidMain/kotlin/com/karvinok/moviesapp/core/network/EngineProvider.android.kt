package com.karvinok.moviesapp.core.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.component.KoinComponent

actual class EngineProvider actual constructor() : KoinComponent {

    actual fun provideEngine(): HttpClientEngine = OkHttp.create()
}
