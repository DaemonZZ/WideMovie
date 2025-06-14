package com.daemonz.common.components.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.daemonz.common.theme.FidoPaletteTokens
import com.daemonz.common.theme.FidoTheme
import org.jetbrains.compose.resources.Font
import widemovie.commonui.generated.resources.Res
import widemovie.commonui.generated.resources.kanit_bold
import widemovie.commonui.generated.resources.kanit_medium
import widemovie.commonui.generated.resources.kanit_semibold
import widemovie.commonui.generated.resources.kanit_thin

@Composable
fun BaseText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = FidoTheme.typography.body1,
    color: Color = FidoTheme.colorScheme.Neutral_TextColors_HighEmp,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    fontFamily: FontFamily = FontFamily.Default,
    fontSize: TextUnit = FidoTheme.typography.body1.fontSize
) = Text(
    text = text,
    modifier = modifier,
    fontFamily = fontFamily,
    style = style,
    color = color,
    maxLines = maxLines,
    minLines = minLines,
    fontSize = fontSize,
)

@Composable
fun rememberFontFamily(): FontFamily = FontFamily(
    Font(resource = Res.font.kanit_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
    Font(resource = Res.font.kanit_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(resource = Res.font.kanit_thin, weight = FontWeight.Thin, style = FontStyle.Normal),
    Font(
        resource = Res.font.kanit_semibold,
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal
    ),
)