package com.app.movie.common.preview

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
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.movie.common.R
import com.app.movie.common.components.buttons.BaseButton
import com.app.movie.common.components.buttons.BaseButtonText
import com.app.movie.common.components.buttons.BaseButtonWithIcon
import com.app.movie.common.components.buttons.BaseOutlinedButton
import com.app.movie.common.components.text.BaseText
import com.app.movie.common.theme.FidoTheme

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
                .background(color = colorScheme.primaryContainer)
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
            style = typography.headlineLarge
        )
        BaseText(
            text = "Headline small styled text",
            style = typography.headlineMedium
        )
        BaseText(
            text = "Headline small styled text",
            style = typography.headlineSmall
        )

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        BaseText(
            text = "Headline small styled text",
            style = typography.bodyLarge
        )
        BaseText(
            text = "Headline small styled text",
            style = typography.bodyMedium
        )
        BaseText(
            text = "Headline small styled text",
            style = typography.bodySmall
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
            icon = painterResource(R.drawable.ic_play),
            shape = shape.medium,
            text = "Like",
            onClick = {}
        )
        BaseOutlinedButton(text = "BaseOutlinedButton", onClick = {}, shape = shape.medium)
    }
}