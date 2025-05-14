package com.daemonz.common.components.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.daemonz.common.theme.FidoTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import widemovie.commonui.generated.resources.Res
import widemovie.commonui.generated.resources.ic_play

@Composable
fun BaseIconButton(
    onClick: () -> Unit,
    src: DrawableResource,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    tintColor: Color = FidoTheme.colorScheme.onPrimary,
) = IconButton(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource
) {
    Icon(painter = painterResource(src), contentDescription = "BaseIconButton", tint = tintColor)
}