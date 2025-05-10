package com.daemonz.common.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

//TODO: Fixme
@Stable
class FidoColorScheme(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val secondaryVariant: Color,
    val background: Color,
    val surface: Color,
    val error: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onError: Color,
    val isLight: Boolean,
) {
    val toMaterialTheme: Colors
        get() = Colors(
            primary = primary,
            primaryVariant = primaryVariant,
            secondary = secondary,
            secondaryVariant = secondaryVariant,
            background = background,
            surface = surface,
            error = error,
            onPrimary = onPrimary,
            onSecondary = onSecondary,
            onBackground = onBackground,
            onSurface = onSurface,
            onError = onError,
            isLight = isLight
        )
}