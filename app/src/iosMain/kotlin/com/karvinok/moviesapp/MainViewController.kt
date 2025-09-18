package com.karvinok.moviesapp

import androidx.compose.ui.window.ComposeUIViewController
import com.karvinok.moviesapp.core.di.initKoin
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }
