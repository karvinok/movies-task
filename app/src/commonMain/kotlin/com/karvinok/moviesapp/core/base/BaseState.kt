package com.karvinok.moviesapp.core.base

import androidx.compose.runtime.Composable

interface BaseState

open class BaseErrorState(
    open val onAction: () -> Unit = {},
    open val onDismiss: () -> Unit = {}
) {
    @Composable
    open fun getTitle(): String = "Something went wrong"

    @Composable
    open fun getDescription(): String? = "Please try again later"

    @Composable
    open fun getConfirmButtonTitle(): String = "Ok"

    @Composable
    open fun getDismissButtonTitle(): String? = null
}
