package com.example.edumi.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = DarkBlue,
    secondary = PurpleGrey80,
    tertiary = Pink80 ,
    onPrimary = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = LightBlue,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onPrimary = Color.White,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun EdumiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    primaryColorPair: PrimaryColorPair? = null,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val defaultDarkColors = DarkColorScheme
    val defaultLightColors = LightColorScheme

    fun applyPrimaryColor(baseScheme: androidx.compose.material3.ColorScheme): androidx.compose.material3.ColorScheme {
        val primaryColor = if (primaryColorPair != null) {
            if (darkTheme) primaryColorPair.dark else primaryColorPair.light
        } else {
            baseScheme.primary
        }
        return baseScheme.copy(primary = primaryColor)
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val dynamicScheme = if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            applyPrimaryColor(dynamicScheme)
        }
        darkTheme -> applyPrimaryColor(defaultDarkColors)
        else -> applyPrimaryColor(defaultLightColors)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}