package com.daemonz.common.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import com.daemonz.common.components.text.BaseText
import com.daemonz.common.theme.FidoTheme

@Composable
fun BaseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = FidoTheme.shapes.medium,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    content: @Composable RowScope.() -> Unit,
) {
    //TODO: customize shape here
    Button(
        shape = shape,
        onClick = onClick,
        content = content,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
    )
}

@Composable
fun BaseButtonText(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = FidoTheme.shapes.medium,
    text: String = "",
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        contentPadding = ButtonDefaults.TextButtonContentPadding
    ) {
        BaseText(text)
    }
}

@Composable
fun BaseButtonWithIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = FidoTheme.shapes.medium,
    text: String = "",
    icon: Painter,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        contentPadding = ButtonDefaults.TextButtonContentPadding
    ) {
        Icon(
            icon,
            contentDescription = "Localized description",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        BaseText(text)
    }
}

@Composable
fun BaseOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = FidoTheme.shapes.medium,
    text: String = "",
) {
    OutlinedButton(modifier = modifier, onClick = onClick, shape = shape) { BaseText(text) }
}
