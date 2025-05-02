package com.daemonz.common.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp


internal object FidoTypeScaleTokens {

//    val KanitFontFamily = FontFamily(
//        Font(Res.font.kanit_regular,weight = FontWeight.Normal, style = FontStyle.Normal),
//        Font(Res.font.kanit_bold,weight = FontWeight.Normal, style = FontStyle.Normal),
//        Font(Res.font.kanit_thin,weight = FontWeight.Normal, style = FontStyle.Normal),
//        Font(Res.font.kanit_semibold,weight = FontWeight.Normal, style = FontStyle.Normal),
//        Font(Res.font.kanit_medium,weight = FontWeight.Normal, style = FontStyle.Normal),
//    )
}

internal object FidoTypographyTokens {

    val SampleTextStyleBodyLarge =
        DefaultTextStyle.copy(
//            fontFamily = FidoTypeScaleTokens.KanitFontFamily(),
            fontWeight = FontWeight.Normal,
        )
    val SampleTextStyle = DefaultTextStyle

    object Display {

        val Bold_36_110 = DefaultTextStyle.copy(
//            fontFamily = FidoTypeScaleTokens.KanitFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
        )

        val Light_36_110 = DefaultTextStyle.copy(
//            fontFamily = FidoTypeScaleTokens.KanitFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp,
        )
    }

    object Headline {

        val Bold_24_110 = DefaultTextStyle.copy(
//            fontFamily = FidoTypeScaleTokens.KanitFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )
        val Light_24_110 = DefaultTextStyle.copy(
//            fontFamily = FidoTypeScaleTokens.KanitFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
        )
    }

    object Title {
        val Bold_20_110 = DefaultTextStyle.copy(
//            fontFamily = FidoTypeScaleTokens.KanitFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        val Light_20_110 = DefaultTextStyle.copy(
//            fontFamily = FidoTypeScaleTokens.KanitFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
        )
    }

    object SubHeader {
        val SubHeader_16_130 = DefaultTextStyle.copy(
//            fontFamily = FidoTypeScaleTokens.KanitFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        )
        val SubHeaderSemiBold_16_130 = DefaultTextStyle.copy(
//            fontFamily = FidoTypeScaleTokens.KanitFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        )
    }

    object Body {
        val SemiBold_14_130 = DefaultTextStyle.copy(
//            fontFamily = FidoTypeScaleTokens.KanitFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
        )
        val Light_10_130 = DefaultTextStyle.copy(
//            fontFamily = FidoTypeScaleTokens.KanitFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        )
        val SmallLight_10_130 = DefaultTextStyle.copy(
//            fontFamily = FidoTypeScaleTokens.KanitFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
        )
    }

    object Small {
        val Smallest_8_130 = DefaultTextStyle.copy(
//            fontFamily = FidoTypeScaleTokens.KanitFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 8.sp,
            lineHeight = 130.sp,
        )
    }

    object Caption {
        val Light12_130 = DefaultTextStyle.copy(
//            fontFamily = FidoTypeScaleTokens.KanitFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 8.sp,
            lineHeight = 130.sp,
        )
    }
}

internal val DefaultLineHeightStyle =
    LineHeightStyle(alignment = LineHeightStyle.Alignment.Center, trim = LineHeightStyle.Trim.None)

internal val DefaultTextStyle = TextStyle.Default.copy(
//    fontFamily = FidoTypeScaleTokens.KanitFontFamily,
    fontWeight = FontWeight.Normal,
    lineHeightStyle = DefaultLineHeightStyle,
)

fun fidoTypography(
    displayHeavy: TextStyle = FidoTypographyTokens.Display.Bold_36_110,
    displayLight: TextStyle = FidoTypographyTokens.Display.Light_36_110,
    headlineLarge: TextStyle = FidoTypographyTokens.Headline.Bold_24_110,
    headlineMedium: TextStyle = FidoTypographyTokens.Headline.Light_24_110,
    titleLarge: TextStyle = FidoTypographyTokens.Title.Bold_20_110,
    titleMedium: TextStyle = FidoTypographyTokens.Title.Light_20_110,
    bodyLarge: TextStyle = FidoTypographyTokens.Body.SemiBold_14_130,
    bodyMedium: TextStyle = FidoTypographyTokens.Body.Light_10_130,
    bodySmall: TextStyle = FidoTypographyTokens.Body.SmallLight_10_130,
): Typography = Typography