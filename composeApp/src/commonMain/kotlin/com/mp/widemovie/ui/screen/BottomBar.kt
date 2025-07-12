package com.mp.widemovie.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.daemonz.common.theme.FidoPaletteTokens
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.ui.screen.home.HomeScreen
import com.mp.widemovie.ui.screen.search.SearchMovie
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import widemovie.composeapp.generated.resources.Res
import widemovie.composeapp.generated.resources.ic_file
import widemovie.composeapp.generated.resources.ic_home
import widemovie.composeapp.generated.resources.ic_search_2


//TODO refactor index later
@Composable
fun AndroidBottomBar(index: Int) {
    var focusState by remember { mutableIntStateOf(index)}
    val bottomBarIcons = arrayOf(
        Res.drawable.ic_home,
//        Res.drawable.ic_download,
        Res.drawable.ic_search_2,
//        Res.drawable.ic_file,
//        Res.drawable.ic_user
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(FidoTheme.colorScheme.onBackground)
    ) {
        bottomBarIcons.forEachIndexed { index, drawableResource ->
            // Custom button here
            val modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(if (focusState == index) FidoPaletteTokens.PrimaryMain else Color.Transparent)
            BaseButtonBottomBar(modifier, drawableResource) {
                focusState = index
            }
        }
    }
    BottomBarNavigation(BottomBarValues.entries.getOrNull(focusState))
}

@Composable
fun BottomBarNavigation(value: BottomBarValues?){
    val nav = LocalNavigator.currentOrThrow
    val currentScreen = nav.lastItem
    when(value){
        BottomBarValues.Home -> {
            if(currentScreen != HomeScreen()){
                nav.push(HomeScreen())
            }
        }
        BottomBarValues.Search -> {
            if(currentScreen != SearchMovie()){
                nav.push(SearchMovie())
            }
        }
        else -> {}
    }
}


@Composable
fun WebNavigatorDrawer() {
    var focusState by remember { mutableIntStateOf(0) }
    // TODO develop later
    val bottomBarIcons = arrayOf(
        Res.drawable.ic_home,
//        Res.drawable.ic_download,
        Res.drawable.ic_search_2,
//        Res.drawable.ic_file,
//        Res.drawable.ic_user
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(0.3f)
            .fillMaxHeight()
            .background(FidoTheme.colorScheme.onBackground),
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))
        bottomBarIcons.forEachIndexed { index, drawableResource ->
            // Custom button here
            val modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(if (focusState == index) FidoPaletteTokens.PrimaryMain else Color.Transparent)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().clickable {
                    focusState = index
                }) {
                BaseButtonBottomBar(modifier, drawableResource) {
                    focusState = index
                }
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Text(text = BottomBarValues.values().getOrNull(index).toString())
            }

        }
    }
    BottomBarNavigation(BottomBarValues.entries.getOrNull(focusState))
}


@Composable
private fun BaseButtonBottomBar(
    modifier: Modifier,
    drawableResource: DrawableResource,
    onClicked: () -> Unit
) {
    Button(
        modifier = Modifier
            .then(modifier),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = onClicked,
        elevation = null
    ) {
        Icon(
            painter = painterResource(drawableResource),
            contentDescription = "icons bottom bar",
            tint = Color.White,
        )
    }
}


enum class BottomBarValues(name: String){
    Home("Home"),
//    Download("Download"),
    Search("Search"),
//    Files("Files"),
//    Profile("Profile")
}
