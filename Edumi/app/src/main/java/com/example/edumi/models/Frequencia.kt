package com.example.edumi.models

import java.time.LocalDate

data class Frequencia (
    val idFilho: Int,
    val presente: Boolean,
    val data : LocalDate,
)

val frequencias = listOf(
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 5, 20)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 5, 21)),
    Frequencia(idFilho = 1, presente = false, data = LocalDate.of(2025, 5, 22)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 5, 23)),
    Frequencia(idFilho = 1, presente = false, data = LocalDate.of(2025, 5, 24)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 5, 25)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 5, 26))
)