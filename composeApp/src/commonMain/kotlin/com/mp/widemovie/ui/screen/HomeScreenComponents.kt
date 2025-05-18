package com.mp.widemovie.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter

@Composable
fun HomeScreen.Header(modifier: Modifier) {
    val imageUrl =
        "https://tse4.mm.bing.net/th/id/OIP.s0JwwoVhmKH3iEkrzz9VpAHaLf?rs=1&pid=ImgDetMain"
    val painter = rememberImagePainter(imageUrl)
    LazyRow(modifier = modifier.background(Color.Gray)) {
        items(10) {
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                modifier = Modifier.width(250.dp),
                painter = painter,
                contentDescription = "image",
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
fun Body(modifier: Modifier) {

}

@Composable
fun Footer(modifier: Modifier) {

}

