package com.karvinok.moviesapp.presentation.moviedetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.karvinok.moviesapp.core.design.Theme
import com.karvinok.moviesapp.core.design.components.LoadingAsyncImage
import com.karvinok.moviesapp.domain.movies.model.CastMember

@Composable
internal fun TopCastBlock(
    cast: List<CastMember>,
    modifier: Modifier = Modifier
) {
    if (cast.isEmpty()) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Top Cast",
            style = Theme.typography.headingMedium,
            color = Theme.colors.text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(cast) { castMember ->
                CastMemberItem(castMember = castMember)
            }
        }
    }
}

@Composable
private fun CastMemberItem(
    castMember: CastMember,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(120.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Theme.colors.surface),
            contentAlignment = Alignment.Center
        ) {
            if (castMember.profilePath != null) {
                LoadingAsyncImage(
                    url = castMember.profilePath,
                    modifier = Modifier.size(100.dp).clip(CircleShape)
                )
            } else {
                Text(
                    text = castMember.name.take(1),
                    style = Theme.typography.headingMedium,
                    color = Theme.colors.text.copy(alpha = 0.5f)
                )
            }
        }

        Text(
            text = castMember.name,
            style = Theme.typography.body01Highlight,
            color = Theme.colors.text,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = castMember.character,
            style = Theme.typography.body01,
            color = Theme.colors.text.copy(alpha = 0.7f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
