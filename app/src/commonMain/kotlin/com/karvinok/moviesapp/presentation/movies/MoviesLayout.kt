@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.karvinok.moviesapp.presentation.movies

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateBounds
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.LookaheadScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.karvinok.moviesapp.core.design.components.LoadingAsyncImage
import com.karvinok.moviesapp.core.design.preview.ValuePP
import com.karvinok.moviesapp.core.design.theme.AppTheme
import com.karvinok.moviesapp.core.design.theme.Theme.colors
import com.karvinok.moviesapp.core.design.theme.Theme.shapes
import com.karvinok.moviesapp.core.design.theme.Theme.spacings
import com.karvinok.moviesapp.core.design.theme.Theme.typography
import com.karvinok.moviesapp.domain.movies.model.Movie
import com.karvinok.moviesapp.presentation.movies.preview.mockMoviesForPreview
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun MoviesLayout(
    state: MoviesState,
    onIntent: (MoviesIntent) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    val lazyMovies = state.moviesFlow.collectAsLazyPagingItems()

    Column(
        modifier = modifier
            .background(color = colors.bg01)
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        var count by rememberSaveable { mutableStateOf(2) }
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacings.s16, vertical = spacings.s8),
                horizontalArrangement = Arrangement.spacedBy(spacings.s8)
            ) {
                Button(
                    onClick = { count = if (count == 2) 3 else 2 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.bg01,
                        contentColor = colors.text01
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.Dataset),
                        contentDescription = "Icon"
                    )
                }
                Button(
                    onClick = { onIntent(MoviesIntent.SwitchMode(MoviesMode.POPULAR)) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.bg01,
                        contentColor = colors.text01
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Popular",
                        style = typography.body01Highlight
                    )
                }

                Button(
                    onClick = { onIntent(MoviesIntent.SwitchMode(MoviesMode.SEARCH)) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.bg01,
                        contentColor = colors.text01
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Search",
                        style = typography.body01Highlight
                    )
                }
            }

            AnimatedVisibility(
                visible = state.currentMode == MoviesMode.SEARCH,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = { onIntent(MoviesIntent.SearchQueryChanged(it)) },
                    shape = shapes.medium,
                    placeholder = {
                        Text("Search movies...", color = colors.text01)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacings.s16, vertical = spacings.s8),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colors.bg01,
                        unfocusedContainerColor = colors.bg01,
                        focusedTextColor = colors.text01,
                        unfocusedTextColor = colors.text01,
                        focusedIndicatorColor = colors.primary,
                        unfocusedIndicatorColor = colors.secondary
                    ),
                    singleLine = true
                )
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            if (!state.isLoading) {
                LookaheadScope {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxSize(),
                        columns = GridCells.Fixed(count),
                        contentPadding = PaddingValues(spacings.s16),
                        horizontalArrangement = Arrangement.spacedBy(spacings.s8),
                        verticalArrangement = Arrangement.spacedBy(spacings.s8)
                    ) {
                        items(
                            count = lazyMovies.itemCount,
                            key = lazyMovies.itemKey { it.id }
                        ) { index ->
                            lazyMovies[index]?.let { movie ->
                                MovieItem(
                                    movie = movie,
                                    isFavorited = state.favoriteIds.contains(movie.id),
                                    onClick = { onIntent(MoviesIntent.MovieClick(movie)) },
                                    modifier = Modifier
                                        .animateItem()
                                        .animateBounds(
                                            this@LookaheadScope,
                                            boundsTransform = BoundsTransform { _, _ ->
                                                spring(
                                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                                    stiffness = Spring.StiffnessMediumLow,
                                                    visibilityThreshold = Rect.VisibilityThreshold,
                                                )
                                            }

                                        ),
                                    onFavoriteClick = {
                                        onIntent(
                                            MoviesIntent.ToggleFavorite(
                                                movie.id
                                            )
                                        )
                                    },
                                    sharedTransitionScope = sharedTransitionScope,
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                            }
                        }
                    }
                }
            }

            if (state.isLoading && lazyMovies.itemCount == 0) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = colors.primary,
                        strokeWidth = spacings.s2
                    )
                }
            }
        }
        AnimatedVisibility(lazyMovies.loadState.append is LoadState.Loading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(spacings.s2),
                strokeCap = StrokeCap.Square,
                trackColor = colors.secondary.copy(alpha = 0.1f),
                color = colors.primary
            )
        }
    }
}

@Composable
internal fun MovieItem(
    movie: Movie,
    isFavorited: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = colors.bg01,
                shape = shapes.medium
            )
            .clip(shapes.medium)
            .clickable { onClick() }
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(2f / 3f)
                .run {
                    with(sharedTransitionScope) {
                        sharedElement(
                            sharedContentState = rememberSharedContentState(key = "movie-image-${movie.id}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                    }
                }
                .clip(shapes.medium)
        ) {
            LoadingAsyncImage(
                url = movie.posterPath,
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.bg01),
                photoAlignment = Alignment.TopCenter,
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

            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .run {
                        with(sharedTransitionScope) {
                            sharedElement(
                                sharedContentState = rememberSharedContentState(key = "movie-fav-icon-${movie.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                        }
                    }
            ) {
                Icon(
                    imageVector = if (isFavorited) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavorited) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavorited) colors.primary else colors.text01,
                    modifier = Modifier.size(spacings.s20)
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(spacings.s8)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Text(
                text = movie.title,
                color = colors.text01,
                style = typography.body01Highlight,
                maxLines = 2,
                modifier = Modifier.run {
                    with(sharedTransitionScope) {
                        sharedBounds(
                            sharedContentState = rememberSharedContentState(key = "movie-title-${movie.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun MoviesLayoutPreview(
    @PreviewParameter(ValuePP::class, 2) value: Int
) {
    val moviesFlow = flowOf(PagingData.from(mockMoviesForPreview))

    AppTheme {
        SharedTransitionLayout {
            AnimatedVisibility(true) {
                MoviesLayout(
                    state = MoviesState(
                        moviesFlow = moviesFlow,
                        currentMode = if (value == 0) MoviesMode.POPULAR else MoviesMode.SEARCH,
                        favoriteIds = setOf(1)
                    ),
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility,
                    onIntent = {}
                )
            }
        }
    }
}
