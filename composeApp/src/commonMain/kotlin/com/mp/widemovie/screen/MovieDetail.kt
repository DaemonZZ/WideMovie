package com.mp.widemovie.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.daemonz.common.components.buttons.BaseButtonText
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.extensions.buildQueryFromParams

data class MovieDetail(val slug: String?) : BaseScreen() {
    override val key = "${super.key}?${buildQueryFromParams(mapOf(
        "film" to slug.toString()
    ))}"
    @Composable
    override fun Content() = FidoTheme {
        val nav = LocalNavigator.currentOrThrow
        BaseButtonText(
            text = "Next",
            onClick = {
                nav += TestScreen("hello")
            }
        )
    }
}
