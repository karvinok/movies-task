package com.karvinok.moviesapp.presentation.moviedetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.karvinok.moviesapp.core.design.Theme
import com.karvinok.moviesapp.core.design.components.LoadingAsyncImage
import com.karvinok.moviesapp.domain.movies.model.MovieImage

@Composable
internal fun PhotosBlock(
    images: List<MovieImage>,
    modifier: Modifier = Modifier
) {
    if (images.isEmpty()) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Photos",
            style = Theme.typography.headingMedium,
            color = Theme.colors.text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(images) { image ->
                PhotoItem(image = image)
            }
        }
    }
}

@Composable
private fun PhotoItem(
    image: MovieImage,
    modifier: Modifier = Modifier
) {
    val aspectRatio = image.width.toFloat() / image.height.toFloat()
    val itemWidth = if (aspectRatio > 1.5f) 280.dp else 200.dp
    val itemHeight = (itemWidth.value / aspectRatio).dp

    Box(
        modifier = modifier
            .width(itemWidth)
            .height(itemHeight.coerceAtLeast(150.dp).coerceAtMost(200.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Theme.colors.surface),
        contentAlignment = Alignment.Center
    ) {
        LoadingAsyncImage(
            url = image.filePath,
            modifier = Modifier
                .width(itemWidth)
                .height(itemHeight.coerceAtLeast(150.dp).coerceAtMost(200.dp))
                .clip(RoundedCornerShape(12.dp))
        )
    }
}
