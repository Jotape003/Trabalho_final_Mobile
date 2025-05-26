package com.example.edumi.models


data class Nota(
    val idFilho : Int,
    val disciplina: String,
    val semestre: String,
    val nota: Double
)


val notas = listOf(
    Nota(1,"Matemática", "1º Semestre", 8.5),
    Nota(1,"Português", "1º Semestre", 7.8),
    Nota(1,"História", "1º Semestre", 9.0),
    Nota(1,"Matemática", "2º Semestre", 9.2),
    Nota(1,"Português", "2º Semestre", 8.0),
    Nota(1,"História", "2º Semestre", 9.5),
)