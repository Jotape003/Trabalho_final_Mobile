package com.example.edumi.models

import java.time.LocalDate

data class Frequencia (
    val idFilho: Int,
    val presente: Boolean,
    val data : LocalDate,
)

val frequencias = listOf(
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 1)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 2)),
    Frequencia(idFilho = 1, presente = false, data = LocalDate.of(2025, 6, 3)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 4)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 5)),
    Frequencia(idFilho = 1, presente = false, data = LocalDate.of(2025, 6, 6)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 7)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 10)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 11)),
    Frequencia(idFilho = 1, presente = false, data = LocalDate.of(2025, 6, 12)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 13)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 14)),
    Frequencia(idFilho = 1, presente = false, data = LocalDate.of(2025, 6, 17)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 18)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 19)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 20)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 21)),
    Frequencia(idFilho = 1, presente = false, data = LocalDate.of(2025, 6, 22)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 23)),
    Frequencia(idFilho = 1, presente = false, data = LocalDate.of(2025, 6, 24)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 25)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 26)),
    Frequencia(idFilho = 1, presente = false, data = LocalDate.of(2025, 6, 27)),
    Frequencia(idFilho = 1, presente = true, data = LocalDate.of(2025, 6, 28))
)
