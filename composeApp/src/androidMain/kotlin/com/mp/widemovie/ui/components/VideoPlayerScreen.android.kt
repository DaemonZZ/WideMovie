package com.mp.widemovie.ui.components

import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.mp.widemovie.MovieApplication
import java.io.File

private const val TAG = "VideoPlayerScreen"

@Composable
actual fun VideoPlayerScreen(videoUrl: String, slug: String) {
    val context = LocalContext.current

    var videofile = File(MovieApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),videoUrl)
    var mediaItem = MediaItem.fromUri(Uri.fromFile(videofile))

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = true
            }
        },
        modifier = Modifier.fillMaxSize()
    )

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
    LaunchedEffect(slug) {
//        ExoPlayer.Builder(context).build().apply {
//            setMediaItem(mediaItem)
//            prepare()
//            playWhenReady = true
//        }
        videofile = File(MovieApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),videoUrl)
        mediaItem = MediaItem.fromUri(Uri.fromFile(videofile))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }
}

@Composable
fun VideoPlayerScreenTs(videoUrl: String) {
    val context = LocalContext.current

    val mediaItem = MediaItem.fromUri(videoUrl)

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = true
            }
        },
        modifier = Modifier.fillMaxSize()
    )

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}

