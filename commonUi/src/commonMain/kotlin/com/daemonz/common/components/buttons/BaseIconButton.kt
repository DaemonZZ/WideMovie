package com.daemonz.common.components.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.daemonz.common.theme.FidoTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import widemovie.commonui.generated.resources.Res
import widemovie.commonui.generated.resources.ic_play

@Composable
fun BaseIconButton(
    onClick: () -> Unit,
    src: DrawableResource,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    tintColor: Color = FidoTheme.colorScheme.Neutral_TextColors_HighEmp,
) = IconButton(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource
) {
    Icon(painter = painterResource(src), contentDescription = contentDescription, tint = tintColor)
}

@Composable
fun BaseIcon(
    src: DrawableResource,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    tint: Color = FidoTheme.colorScheme.Neutral_TextColors_HighEmp
) = Icon(painterResource(src), contentDescription, modifier, tint)