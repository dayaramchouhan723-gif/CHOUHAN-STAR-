package com.anonymous.star.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val CyberDarkColorScheme = darkColorScheme(
    primary = CrimsonPrimary,
    secondary = CrimsonSecondary,
    tertiary = CrimsonTertiary,
    background = BlackBackground,
    surface = DarkSurface,
    surfaceVariant = DarkCard,
    onPrimary = TextWhite,
    onSecondary = TextWhite,
    onBackground = TextWhite,
    onSurface = TextWhite
)

@Composable
fun AnonymousStarTheme(
    themeName: String = "Crimson Core",
    content: @Composable () -> Unit
) {
    val primaryColor = when (themeName) {
        "Cyan Pulse" -> CyanPrimary
        "Aura Red" -> AuraRedPrimary
        "Midnight" -> MidnightPrimary
        else -> CrimsonPrimary
    }

    val secondaryColor = when (themeName) {
        "Cyan Pulse" -> CyanSecondary
        "Aura Red" -> AuraRedSecondary
        "Midnight" -> MidnightSecondary
        else -> CrimsonSecondary
    }

    val colorScheme = CyberDarkColorScheme.copy(
        primary = primaryColor,
        secondary = secondaryColor
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = BlackBackground.toArgb()
            window.navigationBarColor = BlackBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
