package com.example.glare.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val DarkColorScheme = darkColorScheme(
    background = primaryBackgroundColor,
    onBackground = primaryTextColor,

)

@Composable
fun GlareTheme(
    content: @Composable () -> Unit
) {


    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}