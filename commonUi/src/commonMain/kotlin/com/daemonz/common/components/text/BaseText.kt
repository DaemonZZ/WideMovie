package com.daemonz.common.components.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.daemonz.common.theme.FidoTheme

@Composable
fun BaseText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = FidoTheme.typography.body1,
) = Text(
    text = text,
    modifier = modifier,
    style = style,
)