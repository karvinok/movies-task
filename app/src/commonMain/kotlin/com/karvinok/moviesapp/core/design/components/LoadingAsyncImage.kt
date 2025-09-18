package com.karvinok.moviesapp.core.design.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.crossfade

@Composable
fun LoadingAsyncImage(
    url: String? = null,
    modifier: Modifier = Modifier,
    model: Any? = null,
    photoAlignment: Alignment = Alignment.Center,
    errorPainter: Painter? = null,
    shape: Shape? = null,
    cacheKey: String? = url.toString(),
    onSuccess: (() -> Unit)? = null,
    onError: (() -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Crop,
    onLoaded: () -> Unit = {}
) {
    var isPhotoLoading by remember { mutableStateOf(true) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(isPhotoLoading) {
            CircleLoader()
        }

        if (url.isNullOrBlank()) {
            isPhotoLoading = false
            return
        }

        val listener = remember {
            object : ImageRequest.Listener {
                override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                    onLoaded()
                    super.onSuccess(request, result)
                }
            }
        }

        val context = LocalPlatformContext.current

        val imageRequestModel = model ?: remember(url) {
            ImageRequest.Builder(context)
                .data(url)
                .listener(listener)
                .crossfade(true)
                .crossfade(200)
                .diskCacheKey(cacheKey)
                .memoryCachePolicy(if (cacheKey != null) CachePolicy.ENABLED else CachePolicy.DISABLED)
                .diskCachePolicy(if (cacheKey != null) CachePolicy.ENABLED else CachePolicy.DISABLED)
                .build()
        }

        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .run { shape?.let { clip(shape) } ?: this },
            model = imageRequestModel,
            contentScale = contentScale,
            alignment = photoAlignment,
            error = errorPainter,
            onLoading = { isPhotoLoading = true },
            onSuccess = {
                isPhotoLoading = false
                onSuccess?.invoke()
            },
            contentDescription = "AsyncImage",
            onError = {
                isPhotoLoading = false
                onError?.invoke()
            }
        )
    }
}
