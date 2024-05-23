package com.example.peoplelisting.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colorScheme = lightColorScheme(
    primary = AppColor.colorPrimary,
    secondary = AppColor.colorSecondary,
    background = AppColor.colorPrimaryDark,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = typography,
        content = content,
        colorScheme = colorScheme
    )
}
