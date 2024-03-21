package com.example.articleapp.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    surface = Dark,
    onSurface = Light,
    primary = Brand,
    onPrimary = Dark,
    primaryContainer = DarkA,
    onPrimaryContainer = Light,
    secondaryContainer = LightB,
    onSecondaryContainer = Light
)

private val lightColorScheme = lightColorScheme(
    surface = Light,
    onSurface = Dark,
    primary = Brand,
    onPrimary = Dark,
    primaryContainer = LightC,
    onPrimaryContainer = Dark,
    secondaryContainer = DarkB,
    onSecondaryContainer = Dark
)

@Composable
fun ArticleAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) darkColorScheme else lightColorScheme
        }
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.secondaryContainer.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}