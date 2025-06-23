package com.mp.widemovie.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.daemonz.common.components.buttons.BaseIconButton
import com.daemonz.common.components.text.BaseText
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.extensions.rememberAsyncImagePainter
import com.mp.widemovie.ui.uistate.MovieCardUIState
import widemovie.composeapp.generated.resources.Res
import widemovie.composeapp.generated.resources.ic_star

@Composable
fun MovieTabScreen(
    modifier: Modifier = Modifier,
    lisRelatedMovie: List<MovieCardUIState> = emptyList(),
    onItemClick: (MovieCardUIState) -> Unit = {},
) {
    Column(modifier = modifier) {

        // Tabs
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            BaseIconButton(
                src = Res.drawable.ic_star,
                contentDescription = null,
                tintColor = FidoTheme.colorScheme.PrimaryMain,
                onClick = {
                    //TODO
                }
            )
            BaseText(
                text = "Có thể bạn muốn xem?",
                style = FidoTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Grid of movies
        LazyRow(
            modifier = Modifier
                .heightIn(0.dp, max = 500.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            itemsIndexed(lisRelatedMovie) { index, movie ->
                MovieCard(movie, modifier = Modifier.height(200.dp), onClick = {
                    onItemClick(it)
                })
            }
        }
    }
}

data class Movie(val title: String, val imageRes: String, val rating: String)

@Composable
fun MovieCard(
    movie: MovieCardUIState,
    modifier: Modifier = Modifier,
    onClick: (MovieCardUIState) -> Unit,
) {
    // Poster
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.DarkGray)
            .clickable { onClick(movie) }
    ) {
        // Replace with real image loading
        Image(
            painter = rememberAsyncImagePainter(movie.posterUrl), // Add to resources
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(RoundedCornerShape(12.dp)).fillMaxSize()
        )

        // Rating badge
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            BaseText(
                modifier = Modifier
                    .background(Color.Red, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                text = movie.status,
                style = FidoTheme.typography.subtitle1,
            )

            // Title
            BaseText(
                text = movie.title,
                style = FidoTheme.typography.subtitle1,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}