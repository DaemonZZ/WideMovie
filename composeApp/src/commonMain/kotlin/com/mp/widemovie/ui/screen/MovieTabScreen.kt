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
import com.daemonz.common.components.text.BaseText
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.extensions.rememberAsyncImagePainter
import com.mp.widemovie.ui.DemoData.sampleMovies

@Composable
fun MovieTabScreen(
    modifier: Modifier = Modifier,
) {
    val tabs = listOf("Có thể bạn muốn xem", "Bình luận")
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
        LazyRow(
            modifier = Modifier
                .heightIn(0.dp, max = 500.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            itemsIndexed(sampleMovies) { index, movie ->
                MovieCard(movie, modifier = Modifier.height(200.dp))
            }
        }
    }
}

data class Movie(val title: String, val imageRes: String, val rating: String)

@Composable
fun MovieCard(movie: Movie, modifier: Modifier = Modifier) {
    Column {
        // Poster
        Box(
            modifier = modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.DarkGray)
        ) {
            // Replace with real image loading
            Image(
                painter = rememberAsyncImagePainter(movie.imageRes), // Add to resources
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(RoundedCornerShape(12.dp)).fillMaxSize()
            )

            // Rating badge
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomEnd),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                BaseText(
                    modifier = Modifier
                        .background(Color.Red, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    text = movie.rating,
                    style = FidoTheme.typography.subtitle1,
                )
            }
        }

        // Title
        BaseText(
            text = movie.title,
            style = FidoTheme.typography.subtitle1,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}