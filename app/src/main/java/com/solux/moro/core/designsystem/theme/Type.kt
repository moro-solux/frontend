package com.solux.moro.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.solux.moro.R

object PretendardFont {
    val Bold = FontFamily(
        Font(R.font.pretendard_bold)
    )

    val SemiBold = FontFamily(
        Font(R.font.pretendard_semibold)
    )

    val Regular = FontFamily(
        Font(R.font.pretendard_regular)
    )
}

@Immutable
data class Typography(
    // Title
    val titleBold24: TextStyle,
    val titleBold20: TextStyle,

    // SUBTITLE
    val subTitleBold20: TextStyle,
    val subTitleSemiBold18: TextStyle,

    // BODY
    val bodyBold16: TextStyle,
    val bodyBold14: TextStyle,
    val bodyBold12: TextStyle,
    val bodySemiBold16: TextStyle,
    val bodySemiBold14: TextStyle,
    val bodySemiBold12: TextStyle,
    val bodyRegular23: TextStyle,
    val bodyRegular16: TextStyle,
    val bodyRegular14: TextStyle,
    val bodyRegular12: TextStyle,

    // CAPTION
    val captionRegular12: TextStyle,
)

private fun moroTextStyle(
    fontFamily: FontFamily,
    fontSize: TextUnit,
    lineHeight: TextUnit = TextUnit.Unspecified,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null
): TextStyle =
    TextStyle(
        fontFamily = fontFamily,
        fontSize = fontSize,
        lineHeight = lineHeight,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )

val defaultTypography = Typography(
    // Title
    titleBold24 = moroTextStyle(
        fontFamily = PretendardFont.Bold,
        fontSize = 24.sp
    ),
    titleBold20 = moroTextStyle(
        fontFamily = PretendardFont.Bold,
        fontSize = 20.sp
    ),

    // Subtitle
    subTitleBold20 = moroTextStyle(
        fontFamily = PretendardFont.Bold,
        fontSize = 20.sp
    ),
    subTitleSemiBold18 = moroTextStyle(
        fontFamily = PretendardFont.SemiBold,
        fontSize = 18.sp
    ),

    // Body
    bodyBold16 = moroTextStyle(
        fontFamily = PretendardFont.Bold,
        fontSize = 16.sp
    ),
    bodyBold14 = moroTextStyle(
        fontFamily = PretendardFont.Bold,
        fontSize = 14.sp
    ),
    bodyBold12 = moroTextStyle(
        fontFamily = PretendardFont.Bold,
        fontSize = 12.sp
    ),
    bodySemiBold16 = moroTextStyle(
        fontFamily = PretendardFont.SemiBold,
        fontSize = 16.sp
    ),
    bodySemiBold14 = moroTextStyle(
        fontFamily = PretendardFont.SemiBold,
        fontSize = 14.sp
    ),
    bodySemiBold12 = moroTextStyle(
        fontFamily = PretendardFont.SemiBold,
        fontSize = 12.sp
    ),
    bodyRegular23 = moroTextStyle(
        fontFamily = PretendardFont.Regular,
        fontSize = 23.sp
    ),
    bodyRegular16 = moroTextStyle(
        fontFamily = PretendardFont.Regular,
        fontSize = 16.sp
    ),
    bodyRegular14 = moroTextStyle(
        fontFamily = PretendardFont.Regular,
        fontSize = 14.sp
    ),
    bodyRegular12 = moroTextStyle(
        fontFamily = PretendardFont.Regular,
        fontSize = 12.sp
    ),

    // Caption
    captionRegular12 = moroTextStyle(
        fontFamily = PretendardFont.Regular,
        fontSize = 12.sp
    )
)
