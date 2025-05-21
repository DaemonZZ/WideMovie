package com.daemonz.common.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

//TODO: Fixme
@Stable
class FidoColorScheme(
    val onBackground: Color,
    val PrimaryMain: Color = FidoPaletteTokens.PrimaryMain,
    val Primary: Color,
    val Primaryer: Color,
    val Primaryest: Color,
    val Secondary: Color,
    val Secondaryer: Color,
    val Secondaryest: Color,
    val Neutral_TextColors_HighEmp: Color,
    val Neutral_TextColors_MidEmp: Color,
    val Neutral_TextColors_LowEmp: Color,
    val Neutral_TextColorsDisabled: Color,
    val Neutral_TextColorsGreyPurpleHighEmp: Color,
    val Neutral_TextColorsGreyPurpleLowEmp: Color,
    val Neutral_TextColorsGreyPurpleBlack: Color,
    val StatusColorError: Color,
    val StatusColorSuccess: Color,
    val StatusColorWarning: Color,
    val StatusColorInfo: Color,
    val StatusColorAlert: Color,
    val Gradient1_start: Color,
    val Gradient1_center: Color,
    val Gradient1_end: Color,
    val Gradient2_start: Color,
    val Gradient2_end: Color,
    val isLight: Boolean,
) {
    val toMaterialTheme: Colors
        get() = Colors(
            primary = Primary,
            primaryVariant = Primaryer,
            secondary = Secondary,
            secondaryVariant = Secondaryer,
            background = onBackground,
            surface = Primaryest,
            error = StatusColorError,
            onPrimary = Neutral_TextColors_HighEmp,
            onSecondary = Neutral_TextColors_MidEmp,
            onBackground = Neutral_TextColors_HighEmp,
            onSurface = Neutral_TextColors_MidEmp,
            onError = StatusColorError,
            isLight = isLight,
        )
}