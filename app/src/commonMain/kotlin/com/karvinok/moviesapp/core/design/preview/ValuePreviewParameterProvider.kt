package com.karvinok.moviesapp.core.design.preview

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

class ValuePreviewParameterProvider : PreviewParameterProvider<Int> {
    override val values = 0.rangeTo(10).asSequence()
}

typealias ValuePP = ValuePreviewParameterProvider
