package com.example.edumi.models

import com.example.edumi.R

data class Responsavel(
    val id: Int,
    val name: String,
    val filhos: List<Filho>,
    val email: String,
    val telefone: String,
    val imageRes: Int,
    val sexo: String,
    val pais: String
)


val resp = Responsavel(
    id = 1,
    name = "Pedro Joao",
    filhos = listOf(
        Filho(
            id = 1,
            name = "Roberto Facundo",
            idade = 10,
            escola = "Universidade Federal do Ceará",
            foto = R.drawable.robertofacundo,
            turma = "5º Ano - Turma A"
        )
    ),
    email = "pedrojoao1@email.com",
    telefone = "(88) 9 9977-4885",
    imageRes = R.drawable.responsavel,
    sexo = "Masculino",
    pais = "Brasil"
)