package com.example.edumi.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val LightBlue = Color(0xFF5D9FD5)
val DarkBlue = Color(0xFF478AC0)
val PurpleGrey40 = Color(0xFF625B71)
val Pink40 = Color(0xFF7D5260)

data class PrimaryColorPair(
    val light: Color,
    val dark: Color,
    val name: String
)

val PrimaryColorPairs = listOf(
    PrimaryColorPair(
        light = LightBlue,
        dark = DarkBlue,
        name = "Azul"
    ),
    PrimaryColorPair(
        light = Color(0xFF81C784),
        dark = Color(0xFF388E3C),
        name = "Verde"
    ),
    PrimaryColorPair(
        light = Color(0xFFF48FB1),
        dark = Color(0xFFC2185B),
        name = "Rosa"
    ),
    PrimaryColorPair(
        light = Color(0xFFCE93D8),
        dark = Color(0xFF7B1FA2),
        name = "Roxo"
    ),
    PrimaryColorPair(
        light = Color(0xFFFFB74D),
        dark = Color(0xFFF57C00),
        name = "Laranja"
    ),
    PrimaryColorPair(
        light = Color(0xFF4DD0E1), // Ciano claro
        dark = Color(0xFF00796B),  // Ciano escuro
        name = "Ciano"
    )

)
