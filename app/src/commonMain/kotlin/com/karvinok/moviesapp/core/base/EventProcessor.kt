package com.karvinok.moviesapp.core.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
inline fun <T : BaseEvent> EventProcessor(
    events: Flow<T>,
    crossinline block: suspend (T) -> Unit
) {
    LaunchedEffect(Unit) {
        events.collectLatest {
            block(it)
        }
    }
}
