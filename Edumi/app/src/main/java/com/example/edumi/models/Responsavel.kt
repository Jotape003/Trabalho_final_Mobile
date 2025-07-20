package com.example.edumi.models

import com.example.edumi.R

data class Responsavel(
    val id: String,
    val name: String,
    val email: String,
    val telefone: String,
    val imageRes: Int,
    val sexo: String,
    val pais: String
)


val resp = Responsavel(
    id = "99",
    name = "Pedro Joao",
    email = "pedrojoao1@email.com",
    telefone = "(88) 9 9977-4885",
    imageRes = R.drawable.responsavel,
    sexo = "Masculino",
    pais = "Brasil"
)