package com.mp.widemovie.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.daemonz.common.components.buttons.BaseIconButton
import com.daemonz.common.components.text.BaseText
import com.daemonz.common.theme.FidoTheme
import org.jetbrains.compose.resources.painterResource
import widemovie.composeapp.generated.resources.Res
import widemovie.composeapp.generated.resources.banner_ex
import widemovie.composeapp.generated.resources.bookmark_add
import widemovie.composeapp.generated.resources.cast_connected
import widemovie.composeapp.generated.resources.ic_backspace
import widemovie.composeapp.generated.resources.share

@Composable
fun TitleAare(onBackClick: () -> Unit, onShowClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier) {
        BaseIconButton(src = Res.drawable.ic_backspace, onClick = onBackClick)
        BaseIconButton(src = Res.drawable.cast_connected, onClick = onShowClick)
    }
}

@Composable
fun Banner(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(Res.drawable.banner_ex),
        contentDescription = "",
        contentScale = ContentScale.FillBounds,
    )
}

@Composable
fun ContentDetail() {

}

@Composable
fun Title(onBookMarkClick: () -> Unit, onShareClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            BaseText(text = "The Amazing Spider Man", style = FidoTheme.typography.subtitle1)
            BaseIconButton(src = Res.drawable.bookmark_add, onClick = onBookMarkClick)
            BaseIconButton(src = Res.drawable.share, onClick = onShareClick)
        }
    }
}

@Composable
fun SubTitle() {

}

@Composable
fun CastsList() {

}

@Composable
fun EpisodesList() {

}