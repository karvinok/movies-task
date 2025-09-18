package com.karvinok.moviesapp

import android.content.Context
import androidx.startup.Initializer
import com.karvinok.moviesapp.core.di.appModule
import com.karvinok.moviesapp.core.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class AppInitializer : Initializer<KoinApplication> {

    override fun create(context: Context): KoinApplication {
        return initKoin {
            androidContext(context)
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}
