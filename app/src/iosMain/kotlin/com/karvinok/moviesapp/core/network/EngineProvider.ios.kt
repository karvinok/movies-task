package com.karvinok.moviesapp.core.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.engine.darwin.KtorNSURLSessionDelegate
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import platform.Foundation.NSURLSession

private val delegate by lazy { KtorNSURLSessionDelegate() }

actual class EngineProvider actual constructor() : KoinComponent {
    actual fun provideEngine(): HttpClientEngine {
        val session =
            provideURLSession()?.takeIf { it.delegate == delegate }

        if (session == null) {
            println("Creating a default URLSession. A custom URLSession was not provided, or the provided delegate was not of the expected KtorNSURLSessionDelegate type.")
        }

        return Darwin.create {
            session?.let {
                usePreconfiguredSession(it, delegate)
            }
        }
    }

    private fun provideURLSession(): NSURLSession? = try {
        get<NSURLSession>()
    } catch (e: Exception) {
        null
    }
}

fun provideKtorUrlSessionDelegate() = delegate
