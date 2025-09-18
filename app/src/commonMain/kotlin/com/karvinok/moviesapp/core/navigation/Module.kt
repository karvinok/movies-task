package com.karvinok.moviesapp.core.navigation

import org.koin.dsl.module

val navigationModule = module {
    single<Navigator> { ComposeNavigator() }
}
