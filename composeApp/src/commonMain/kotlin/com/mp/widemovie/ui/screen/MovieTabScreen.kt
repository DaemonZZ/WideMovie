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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mp.widemovie.extensions.rememberAsyncImagePainter
import com.daemonz.common.components.text.BaseText
import com.daemonz.common.theme.FidoTheme

@Composable
fun MovieTabScreen(
    modifier: Modifier = Modifier,
) {
    val tabs = listOf("Trailers", "More like This", "Comments")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(modifier = modifier) {

        // Tabs
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                BaseText(
                    text = title,
                    color = if (index == selectedTabIndex) Color.Red else Color.White,
                    style = FidoTheme.typography.h1.copy(fontWeight = if (index == selectedTabIndex) FontWeight.Bold else FontWeight.Normal),
                    modifier = Modifier
                        .clickable { selectedTabIndex = index }
                        .padding(bottom = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Grid of movies
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .heightIn(0.dp, max = 500.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(sampleMovies) { index, movie ->
                MovieCard(movie)
            }
        }
    }
}

data class Movie(val title: String, val imageRes: String, val rating: String)

val sampleMovies = listOf(
    Movie("Pushpa", "pushpa.jpg", "8.5"),
    Movie("Oblivion", "oblivion.jpg", "8.5"),
    Movie("Fallout", "fallout.jpg", "8.5"),
    Movie("Top Gun Maverick", "topgun.jpg", "8.5"),
    Movie("Pushpa", "pushpa.jpg", "8.5"),
    Movie("Oblivion", "oblivion.jpg", "8.5"),
    Movie("Fallout", "fallout.jpg", "8.5"),
    Movie("Top Gun Maverick", "topgun.jpg", "8.5"),
)

@Composable
fun MovieCard(movie: Movie) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.DarkGray)
    ) {
        Column {
            // Poster
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                // Replace with real image loading
                Image(
                    painter = rememberAsyncImagePainter(movie.imageRes), // Add to resources
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(RoundedCornerShape(12.dp))
                )

                // Rating badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(6.dp)
                        .background(Color.Red, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    BaseText(
                        text = movie.rating,
                        style = FidoTheme.typography.subtitle1,
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Title
            BaseText(
                text = movie.title,
                style = FidoTheme.typography.subtitle1,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}