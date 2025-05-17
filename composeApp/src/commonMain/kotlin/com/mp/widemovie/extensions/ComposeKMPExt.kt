package com.mp.widemovie.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.seiko.imageloader.rememberImagePainter

@Composable
fun rememberAsyncImagePainter(imageUrl: String): Painter = rememberImagePainter(imageUrl)