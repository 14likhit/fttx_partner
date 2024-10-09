package com.fttx.partner.ui.compose.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.fttx.partner.R

val FontWeightThin = FontWeight(200)
val FontWeightRegular = FontWeight(450)
val FontWeightMedium = FontWeight(550)
val FontWeightBold = FontWeight(700)

private val Montserrat =
    FontFamily(
        Font(R.font.montserrat_thin, FontWeightThin),
        Font(R.font.montserrat_regular, FontWeightRegular),
        Font(R.font.montserrat_medium, FontWeightMedium),
        Font(R.font.montserrat_bold, FontWeightBold),
    )

private val FTTXText =
    TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeightRegular,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    )

val Caption01Regular =
    FTTXText.copy(
        fontSize = 12.sp,
        lineHeight = 18.sp,
    )

val Caption01Bold =
    Caption01Regular.copy(
        fontWeight = FontWeightBold,
    )

val Caption02Regular =
    FTTXText.copy(
        fontSize = 10.sp,
        lineHeight = 16.sp,
    )

val Caption02Bold =
    Caption02Regular.copy(
        fontWeight = FontWeightBold,
    )

val Caption02Medium =
    Caption02Regular.copy(
        fontWeight = FontWeightMedium,
    )

val Tiny01Regular =
    FTTXText.copy(
        fontSize = 8.sp,
        lineHeight = 14.sp,
    )

val Tiny01Bold =
    Tiny01Regular.copy(
        fontWeight = FontWeightBold,
    )

val Tiny01BoldItalic =
    Tiny01Bold.copy(
        fontWeight = FontWeightBold,
        fontStyle = FontStyle.Italic,
    )

val TinySmallBold =
    FTTXText.copy(
        fontSize = 6.sp,
        lineHeight = 12.sp,
        fontWeight = FontWeightBold,
    )

val TinySmall01BoldItalic =
    TinySmallBold.copy(
        fontWeight = FontWeightBold,
        fontStyle = FontStyle.Italic,
    )

val PriceStrikethrough =
    FTTXText.copy(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        textDecoration = TextDecoration.LineThrough,
    )

val Text01Regular =
    FTTXText.copy(
        fontSize = 16.sp,
        lineHeight = 22.sp,
    )

val Text01Medium =
    Text01Regular.copy(
        fontWeight = FontWeightMedium,
    )

val Text01Bold =
    Text01Regular.copy(
        fontWeight = FontWeightBold,
    )

val Text02Regular =
    FTTXText.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
    )

val Text02Medium =
    Text02Regular.copy(
        fontWeight = FontWeightMedium,
    )

val Text02Bold =
    Text02Regular.copy(
        fontWeight = FontWeightBold,
    )

val Text02BoldItalic =
    Text02Regular.copy(
        fontWeight = FontWeightBold,
        fontStyle = FontStyle.Italic,
    )

val Subheading01Medium =
    FTTXText.copy(
        fontSize = 18.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeightMedium,
    )

val Subheading01Bold =
    Subheading01Medium.copy(
        fontWeight = FontWeightBold,
    )

val Subheading02Medium =
    FTTXText.copy(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeightMedium,
    )

val Subheading03Medium =
    FTTXText.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeightMedium,
    )

val Subheading03Bold =
    Subheading03Medium.copy(
        fontWeight = FontWeightBold,
    )

val Heading01Medium =
    FTTXText.copy(
        fontSize = 32.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeightMedium,
    )

val Heading02Medium =
    FTTXText.copy(
        fontSize = 28.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeightMedium,
    )

val Heading03Medium =
    FTTXText.copy(
        fontSize = 24.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeightMedium,
    )
