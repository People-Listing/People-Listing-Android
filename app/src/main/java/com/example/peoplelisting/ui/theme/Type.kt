package com.example.peoplelisting.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.peoplelisting.R

val din = FontFamily(
    listOf(
        Font(R.font.din_regular),
        Font(R.font.din_bold, weight = FontWeight.Bold),
        Font(R.font.din_medium, weight = FontWeight.Medium),
    )
)

val gilroy = FontFamily(Font(R.font.gilroy_extra_bold, weight = FontWeight.ExtraBold))

val typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = din,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)