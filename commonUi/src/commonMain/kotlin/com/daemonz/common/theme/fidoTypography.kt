package com.daemonz.common.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

internal object FidoTypographyTokens {

    val SampleTextStyleBodyLarge =
        DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
        )
    val SampleTextStyle = DefaultTextStyle

    object Display {

        val Bold_36_110 = DefaultTextStyle.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
        )

        val Light_36_110 = DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp,
        )
    }

    object Headline {

        val Bold_24_110 = DefaultTextStyle.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )
        val Light_24_110 = DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
        )
    }

    object Title {
        val Bold_20_110 = DefaultTextStyle.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        val Light_20_110 = DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
        )
    }

    object SubHeader {
        val SubHeader_16_130 = DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        )
        val SubHeaderSemiBold_16_130 = DefaultTextStyle.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        )
    }

    object Body {
        val SemiBold_14_130 = DefaultTextStyle.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
        )
        val Light_10_130 = DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        )
        val SmallLight_10_130 = DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
        )
    }

    object Small {
        val Smallest_8_130 = DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 8.sp,
        )

        val SmallLight_10_130 = DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
        )
    }

    object Caption {
        val Light12_130 = DefaultTextStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 8.sp,
        )
    }
}

internal val DefaultLineHeightStyle =
    LineHeightStyle(alignment = LineHeightStyle.Alignment.Center, trim = LineHeightStyle.Trim.None)

internal val DefaultTextStyle = TextStyle.Default.copy(
    fontWeight = FontWeight.Normal,
    lineHeightStyle = DefaultLineHeightStyle,
)

fun fidoTypography(
    title1: TextStyle = FidoTypographyTokens.Title.Bold_20_110,
    title2: TextStyle = FidoTypographyTokens.Title.Light_20_110,
    title3: TextStyle = FidoTypographyTokens.Headline.Bold_24_110,
    title4: TextStyle = FidoTypographyTokens.Small.SmallLight_10_130,
    title6: TextStyle = FidoTypographyTokens.Small.Smallest_8_130,
    subHeader1: TextStyle = FidoTypographyTokens.SubHeader.SubHeaderSemiBold_16_130,
    subHeader2: TextStyle = FidoTypographyTokens.Body.SemiBold_14_130,
    bodyLarge: TextStyle = FidoTypographyTokens.Body.SemiBold_14_130,
    bodyMedium: TextStyle = FidoTypographyTokens.Body.Light_10_130,
    bodySmall: TextStyle = FidoTypographyTokens.SubHeader.SubHeaderSemiBold_16_130,
    caption: TextStyle = FidoTypographyTokens.Small.SmallLight_10_130,
): Typography = Typography(
    h1 = title1,
    h2 = title2,
    h3 = title3,
    h4 = title4,
    h6 = title6,
    subtitle1 = subHeader1,
    subtitle2 = subHeader2,
    body1 = bodyLarge,
    body2 = bodyMedium,
    button = bodySmall,
    caption = caption,
)