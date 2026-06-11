package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val HighDensityColorScheme = lightColorScheme(
    primary = HighDensityPurple,
    onPrimary = Color.White,
    primaryContainer = HighDensityActivePill,
    onPrimaryContainer = HighDensityActiveText,
    secondary = HighDensityPurpleLight,
    onSecondary = Color.White,
    tertiary = RadiantEmerald,
    background = HighDensityBg,
    onBackground = HighDensityText,
    surface = SlateStellarCard,
    onSurface = HighDensityText,
    surfaceVariant = HighDensityContainerBg,
    onSurfaceVariant = HighDensityGreyText,
    outline = HighDensityBorder,
    error = Color(0xFFB3261E)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false, // Enforce light high density theme as requested by user
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // We enforce our unified high density Material 3 light theme for a polished modern look
    MaterialTheme(
        colorScheme = HighDensityColorScheme,
        typography = Typography,
        content = content
    )
}
