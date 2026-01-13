package com.karvinok.moviesapp.presentation.moviedetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karvinok.moviesapp.core.design.theme.Theme.colors
import com.karvinok.moviesapp.core.design.theme.Theme.typography

@Composable
internal fun UserReviewsBlock(
    voteAverage: Double,
    voteCount: Int,
    modifier: Modifier = Modifier
) {
    if (voteCount == 0) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(colors.surface01)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "User reviews",
                style = typography.headingMedium,
                color = colors.text01
            )

            Text(
                text = formatVoteCount(voteCount),
                style = typography.body01,
                color = colors.text01.copy(alpha = 0.7f)
            )
        }

        // Rating section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating",
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(48.dp)
            )

            Column(
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Text(
                    text = String.format("%.1f", voteAverage),
                    style = typography.headingMedium.copy(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = colors.text01
                )

                Text(
                    text = "/ 10",
                    style = typography.body01,
                    color = colors.text01.copy(alpha = 0.7f)
                )
            }
        }

        // Summary
        Text(
            text = "Summary",
            style = typography.body01Highlight,
            color = colors.text01,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = generateSummary(voteAverage, voteCount),
            style = typography.body01,
            color = colors.text01.copy(alpha = 0.8f),
            lineHeight = 20.sp
        )
    }
}

private fun formatVoteCount(count: Int): String {
    return when {
        count >= 1000000 -> String.format("%.1fM", count / 1000000.0)
        count >= 1000 -> String.format("%.1fK", count / 1000.0)
        else -> count.toString()
    }
}

private fun generateSummary(voteAverage: Double, voteCount: Int): String {
    val rating = when {
        voteAverage >= 8.0 -> "highly acclaimed"
        voteAverage >= 7.0 -> "well-received"
        voteAverage >= 6.0 -> "positively reviewed"
        voteAverage >= 5.0 -> "mixed reviews"
        else -> "varied reception"
    }

    return "This movie has received $rating from audiences with ${formatVoteCount(voteCount)} total votes. " +
            "The average rating of ${String.format("%.1f", voteAverage)}/10 reflects the overall viewer sentiment."
}
