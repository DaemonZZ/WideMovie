package com.app.movie.common.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.app.movie.common.theme.FidoTheme

@Composable
fun BaseText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = FidoTheme.typography.bodyMedium,
) = Text(
    text = text,
    modifier = modifier,
    style = style,
)