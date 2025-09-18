@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.karvinok.moviesapp.presentation.moviedetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.karvinok.moviesapp.core.design.components.LoadingAsyncImage
import com.karvinok.moviesapp.core.design.components.RoundButton
import com.karvinok.moviesapp.core.design.preview.ValuePP
import com.karvinok.moviesapp.core.design.shimmer
import com.karvinok.moviesapp.core.design.theme.AppTheme
import com.karvinok.moviesapp.core.design.theme.Theme.colors
import com.karvinok.moviesapp.core.design.theme.Theme.shapes
import com.karvinok.moviesapp.core.design.theme.Theme.spacings
import com.karvinok.moviesapp.core.design.theme.Theme.typography
import com.karvinok.moviesapp.domain.movies.model.Movie
import com.karvinok.moviesapp.presentation.movies.preview.mockMoviesForPreview
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun MovieDetailsLayout(
    state: MovieDetailsState,
    onIntent: (MovieDetailsIntent) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colors.bg01)
            .verticalScroll(rememberScrollState())
    ) {
        HeaderBlock(
            movie = state.movie,
            isFavorite = state.isFavorite,
            onBackClick = { onIntent(MovieDetailsIntent.BackClick) },
            onFavoriteClick = { onIntent(MovieDetailsIntent.ToggleFavorite) },
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = animatedVisibilityScope
        )

        DescriptionBlock(
            releaseDate = state.releaseDate,
            genre = state.genre,
            rating = state.rating,
            overview = state.overview,
            isLoadingDetails = state.isLoading
        )

        AnimatedVisibility(state.isLoading) {
            Spacer(
                modifier = Modifier
                    .padding(spacings.s16)
                    .height(50.dp)
                    .width(250.dp)
                    .shimmer(true)
            )
        }
        AnimatedVisibility(!state.isLoading) {
            LanguagesBlock(
                languages = state.spokenLanguages,
                onClick = { onIntent(MovieDetailsIntent.ShowLanguagesModal) }
            )
        }

        if (state.showLanguagesDialog) {
            LanguagesDialog(
                languages = state.spokenLanguages,
                onDismiss = { onIntent(MovieDetailsIntent.DismissLanguagesModal) }
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun HeaderBlock(
    movie: Movie,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(550.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .run {
                    with(sharedTransitionScope) {
                        sharedElement(
                            sharedContentState = rememberSharedContentState(key = "movie-image-${movie.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }
                }
                .clip(shapes.none)
        ) {
            LoadingAsyncImage(
                url = movie.posterPath,
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.bg01),
                contentScale = ContentScale.Crop,
                photoAlignment = Alignment.TopCenter
            )

            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                colors.bg01.copy(alpha = 0.3f),
                                colors.bg01.copy(alpha = 1f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
        }

        Text(
            text = movie.title,
            style = typography.headingMedium,
            color = colors.text01,
            maxLines = 2,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = spacings.s16)
                .run {
                    with(sharedTransitionScope) {
                        sharedBounds(
                            sharedContentState = rememberSharedContentState(key = "movie-title-${movie.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }
                }
        )

        RoundButton(
            painter = rememberVectorPainter(Icons.Default.ArrowBack),
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(spacings.s16),
            bgColor = colors.bgBlackOp40,
            tint = colors.primary,
            onClick = onBackClick
        )

        RoundButton(
            painter = rememberVectorPainter(if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(spacings.s16)
                .run {
                    with(sharedTransitionScope) {
                        sharedElement(
                            sharedContentState = rememberSharedContentState(key = "movie-fav-icon-${movie.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }
                },
            bgColor = colors.bgBlackOp40,
            tint = colors.primary,
            onClick = onFavoriteClick
        )
    }
}

@Composable
fun DescriptionBlock(
    releaseDate: String?,
    genre: String?,
    rating: String?,
    overview: String?,
    isLoadingDetails: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacings.s16),
    ) {
        Spacer(Modifier.height(spacings.s12))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacings.s16)
        ) {
            rating?.let {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacings.s4)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            releaseDate?.let {
                Text(
                    text = it,
                    style = typography.body01,
                    color = colors.text02,
                )
            }

            genre?.let {
                Text(
                    text = it,
                    style = typography.body01,
                    color = colors.text02,
                )
            }
        }
        Spacer(Modifier.height(spacings.s16))
        if (isLoadingDetails) {
            Spacer(
                modifier = Modifier
                    .height(30.dp)
                    .width(150.dp)
                    .shimmer(true)
            )
        } else {
            overview?.let {
                Text(
                    text = "Overview",
                    style = typography.body01,
                    color = colors.text01
                )
                Spacer(Modifier.height(spacings.s8))
                Text(
                    text = it,
                    style = typography.body01,
                    color = colors.text01,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun LanguagesBlock(
    languages: List<String>,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = spacings.s16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.height(spacings.s24))
        Text(
            text = "Languages: ",
            style = typography.body01Highlight,
            color = colors.text01
        )

        Spacer(Modifier.height(spacings.s8))

        TextButton(onClick = onClick) {
            Text(
                text = "${languages.size} language(s) available",
                style = typography.body01,
                color = colors.primary
            )
        }
    }
}

@Composable
private fun LanguagesDialog(
    languages: List<String>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Available Languages:",
                style = typography.body01Highlight,
                color = colors.text01
            )
        },
        text = {
            Text(
                text = languages.joinToString("\n"),
                style = typography.body01,
                color = colors.text01
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "OK",
                    color = colors.primary
                )
            }
        },
        containerColor = colors.secondary.copy(0.7f)
    )
}

@Preview
@Composable
private fun MovieDetailsLayoutPreview(
    @PreviewParameter(ValuePP::class, 3) value: Int
) {
    AppTheme {
        SharedTransitionLayout {
            AnimatedVisibility(true) {
                MovieDetailsLayout(
                    state = MovieDetailsState(
                        movie = mockMoviesForPreview.first(),
                        isLoading = value == 1,
                        overview = "Overview ".repeat(100),
                        releaseDate = "2029",
                        showLanguagesDialog = value == 2,
                        genre = if (value == 0) "Comedy" else null,
                        rating = "3.4",
                        spokenLanguages = listOf("En, Es")
                    ),
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility,
                    onIntent = {}
                )
            }
        }
    }
}
