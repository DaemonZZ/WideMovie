package com.daemonz.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import com.daemonz.common.theme.dark.fidoDarkColorScheme
import com.daemonz.common.theme.light.fidoLightColorScheme

internal val LocalTypography = staticCompositionLocalOf { fidoTypography() }
internal val LocalColorScheme = staticCompositionLocalOf { lightColors() }
internal val LocalShapes = staticCompositionLocalOf { Shapes() }
internal val LocalUsingExpressiveTheme = staticCompositionLocalOf { false }

object FidoTheme {
    /**
     * Retrieves the current [ColorScheme] at the call site's position in the hierarchy.
     */
    val colorScheme: Colors
        @Composable @ReadOnlyComposable get() = LocalColorScheme.current

    /**
     * Retrieves the current [kotlin.text.Typography] at the call site's position in the hierarchy.
     */
    val typography: Typography
        @Composable @ReadOnlyComposable get() = LocalTypography.current

    /**
     * Retrieves the current [Shapes] at the call site's position in the hierarchy.
     */
    val shapes: Shapes
        @Composable @ReadOnlyComposable get() = LocalShapes.current
}

@Composable
fun FidoTheme(
    customizeColorScheme: Colors? = null,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    typography: Typography? = null,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        customizeColorScheme != null -> customizeColorScheme
        isDarkTheme -> fidoDarkColorScheme()
        else -> fidoLightColorScheme()
    }

    val shapes = Shapes(
        large = RoundedCornerShape(6.0.dp),
        medium = RoundedCornerShape(6.0.dp),
        small = RoundedCornerShape(6.0.dp),
    )

    val currentTypography = typography ?: fidoTypography()
    val isExpressiveTheme = customizeColorScheme != null

    CompositionLocalProvider(
        LocalTypography provides currentTypography,
        LocalColorScheme provides colorScheme,
        LocalShapes provides shapes,
        LocalUsingExpressiveTheme provides isExpressiveTheme,
    ) {
        MaterialTheme(
            colors = FidoTheme.colorScheme,
            shapes = FidoTheme.shapes,
//            typography = FidoTheme.typography,
            content = content
        )
    }
}

@Composable
/*@VisibleForTesting*/
internal fun rememberTextSelectionColors(colorScheme: Colors): TextSelectionColors {
    val primaryColor = colorScheme.primary
    return remember(primaryColor) {
        TextSelectionColors(
            handleColor = primaryColor,
            backgroundColor = primaryColor.copy(alpha = TextSelectionBackgroundOpacity),
        )
    }
}

/*@VisibleForTesting*/
internal const val TextSelectionBackgroundOpacity = 0.4f