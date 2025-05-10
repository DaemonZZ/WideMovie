package com.daemonz.common.preview

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daemonz.common.components.buttons.BaseButton
import com.daemonz.common.components.buttons.BaseButtonText
import com.daemonz.common.components.buttons.BaseButtonWithIcon
import com.daemonz.common.components.buttons.BaseOutlinedButton
import com.daemonz.common.components.text.BaseText
import com.daemonz.common.theme.FidoTheme
import widemovie.commonui.generated.resources.Res
import widemovie.commonui.generated.resources.ic_play

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = false)
annotation class FidoThemePreview

@FidoThemePreview
@Composable
fun FidoThemeSample() = FidoTheme {
    val currentTheme = if (!isSystemInDarkTheme()) "light" else "dark"
    ExtendedFloatingActionButton(
        text = { Text("FAB with text style and color from $currentTheme expressive theme") },
        icon = { Icon(Icons.Filled.Favorite, contentDescription = "Localized Description") },
        onClick = {}
    )
}

@FidoThemePreview
@Composable
fun ThemeColorSample() = FidoTheme {
    val colorScheme = FidoTheme.colorScheme
    Column {
        Box(
            Modifier
                .aspectRatio(1f)
                .size(10.dp, 10.dp)
                .background(color = colorScheme.primary)
        )
        Box(
            Modifier
                .aspectRatio(1f)
                .size(10.dp, 10.dp)
                .background(color = colorScheme.onPrimary)
        )
        Box(
            Modifier
                .aspectRatio(1f)
                .size(10.dp, 10.dp)
                .background(color = colorScheme.secondary)
        )
        Box(
            Modifier
                .aspectRatio(1f)
                .size(10.dp, 10.dp)
                .background(color = colorScheme.primary)
        )
    }
}

@FidoThemePreview
@Composable
fun ThemeTextStyleSample() = FidoTheme {
    val typography = FidoTheme.typography
    Column(
        Modifier
            .fillMaxSize()
    ) {
        BaseText(
            text = "Headline small styled text",
            style = typography.h1
        )
        BaseText(
            text = "Headline small styled text",
            style = typography.h2
        )
        BaseText(
            text = "Headline small styled text",
            style = typography.h3
        )

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        BaseText(
            text = "Headline small styled text",
            style = typography.body1
        )
        BaseText(
            text = "Headline small styled text",
            style = typography.body2
        )
        BaseText(
            text = "Headline small styled text",
            style = typography.button
        )
    }
}

@FidoThemePreview
@Composable
fun ThemeShapeSample() = FidoTheme {
    val shape = FidoTheme.shapes
    Column {
        BaseButton(onClick = {}) {
            Text("Button ${shape.small} shape")
        }
        BaseButtonText(onClick = {}, text = "Fill Button")
        BaseButtonWithIcon(
            icon = painterResource(Res.drawable.ic_play),
            shape = shape.medium,
            text = "Like",
            onClick = {}
        )
        BaseOutlinedButton(text = "BaseOutlinedButton", onClick = {}, shape = shape.medium)
    }
}