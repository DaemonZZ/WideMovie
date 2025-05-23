package com.daemonz.common.theme.light

import androidx.compose.ui.graphics.Color
import com.daemonz.common.theme.FidoColorScheme
import com.daemonz.common.theme.dark.ColorDarkTokens

/** Returns a light Material color scheme. */
fun fidoLightColorScheme(
    onBackground: Color = ColorDarkTokens.Secondaryest,
    Primary: Color = ColorLightTokens.Primary,
    Primaryer: Color = ColorLightTokens.Primaryer,
    Primaryest: Color = ColorLightTokens.Primaryest,
    Secondary: Color = ColorLightTokens.Secondary,
    Secondaryer: Color = ColorLightTokens.Secondaryer,
    Secondaryest: Color = ColorLightTokens.Secondaryest,
    Neutral_TextColors_HighEmp: Color = ColorLightTokens.Neutral_TextColors_HighEmp,
    Neutral_TextColors_MidEmp: Color = ColorLightTokens.Neutral_TextColors_MidEmp,
    Neutral_TextColors_LowEmp: Color = ColorLightTokens.Neutral_TextColors_LowEmp,
    Neutral_TextColorsDisabled: Color = ColorLightTokens.Neutral_TextColorsDisabled,
    Neutral_TextColorsGreyPurpleHighEmp: Color = ColorLightTokens.Neutral_TextColorsGreyPurpleHighEmp,
    Neutral_TextColorsGreyPurpleLowEmp: Color = ColorLightTokens.Neutral_TextColorsGreyPurpleLowEmp,
    Neutral_TextColorsGreyPurpleBlack: Color = ColorLightTokens.Neutral_TextColorsGreyPurpleBlack,
    StatusColorError: Color = ColorLightTokens.StatusColorError,
    StatusColorSuccess: Color = ColorLightTokens.StatusColorSuccess,
    StatusColorWarning: Color = ColorLightTokens.StatusColorWarning,
    StatusColorInfo: Color = ColorLightTokens.StatusColorInfo,
    StatusColorAlert: Color = ColorLightTokens.StatusColorAlert,
    Gradient1_start: Color = ColorLightTokens.Gradient1_start,
    Gradient1_center: Color = ColorLightTokens.Gradient1_center,
    Gradient1_end: Color = ColorLightTokens.Gradient1_end,
    Gradient2_start: Color = ColorLightTokens.Gradient2_start,
    Gradient2_end: Color = ColorLightTokens.Gradient2_end,
): FidoColorScheme = FidoColorScheme(
    onBackground = onBackground,
    Primary = Primary,
    Primaryer = Primaryer,
    Primaryest = Primaryest,
    Secondary = Secondary,
    Secondaryer = Secondaryer,
    Secondaryest = Secondaryest,
    Neutral_TextColors_HighEmp = Neutral_TextColors_HighEmp,
    Neutral_TextColors_MidEmp = Neutral_TextColors_MidEmp,
    Neutral_TextColors_LowEmp = Neutral_TextColors_LowEmp,
    Neutral_TextColorsDisabled = Neutral_TextColorsDisabled,
    Neutral_TextColorsGreyPurpleHighEmp = Neutral_TextColorsGreyPurpleHighEmp,
    Neutral_TextColorsGreyPurpleLowEmp = Neutral_TextColorsGreyPurpleLowEmp,
    Neutral_TextColorsGreyPurpleBlack = Neutral_TextColorsGreyPurpleBlack,
    StatusColorError = StatusColorError,
    StatusColorSuccess = StatusColorSuccess,
    StatusColorWarning = StatusColorWarning,
    StatusColorInfo = StatusColorInfo,
    StatusColorAlert = StatusColorAlert,
    Gradient1_start = Gradient1_start,
    Gradient1_center = Gradient1_center,
    Gradient1_end = Gradient1_end,
    Gradient2_start = Gradient2_start,
    Gradient2_end = Gradient2_end,
    isLight = true,
)
