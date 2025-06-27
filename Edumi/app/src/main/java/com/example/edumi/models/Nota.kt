package com.example.edumi.models


data class Nota(
    val idFilho : Int,
    val disciplina: String,
    val semestre: String,
    val nota: Double
)


val notas = listOf(
    Nota(1,"Matemática", "2025.1", 8.5),
    Nota(1,"Português", "2025.1", 7.8),
    Nota(1,"História", "2025.1", 9.0),
    Nota(1,"Matemática", "2025.2", 9.2),
    Nota(1,"Português", "2025.2", 8.0),
    Nota(1,"História", "2025.2", 9.5),
)