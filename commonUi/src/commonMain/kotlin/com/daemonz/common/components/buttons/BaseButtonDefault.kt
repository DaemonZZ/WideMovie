package com.daemonz.common.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults.OutlinedBorderSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.daemonz.common.theme.FidoTheme

object BaseButtonDefault {
    val outlinedBorder: BorderStroke
        @Composable
        get() = BorderStroke(
            OutlinedBorderSize, FidoTheme.colorScheme.PrimaryMain,
        )

    @Composable
    fun outlinedButtonColors(
        backgroundColor: Color = Color.Transparent,
        contentColor: Color = FidoTheme.colorScheme.Primary,
        disabledContentColor: Color = FidoTheme.colorScheme.Neutral_TextColorsDisabled,
    ): ButtonColors = BaseButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = backgroundColor,
        disabledContentColor = disabledContentColor
    )

    @Composable
    fun buttonPrimaryColors(
        backgroundColor: Color = FidoTheme.colorScheme.Primary,
        contentColor: Color = FidoTheme.colorScheme.Primary,
        disabledContentColor: Color = FidoTheme.colorScheme.Neutral_TextColorsDisabled,
    ): ButtonColors = BaseButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = backgroundColor,
        disabledContentColor = disabledContentColor
    )
}