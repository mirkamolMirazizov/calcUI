
package com.example.multicalculator.android.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val colorPalette = lightColors(
    primary = Blue500,
    primaryVariant = Blue700,
    secondary = Green200
)

@Composable
fun MultiCalculatorTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = colorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
