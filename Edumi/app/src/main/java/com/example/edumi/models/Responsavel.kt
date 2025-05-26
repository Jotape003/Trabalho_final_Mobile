package com.example.edumi.models

import com.example.edumi.R

data class Responsavel(
    val id: Int,
    val name: String,
    val filhos: List<Filho>,
    val email: String,
    val telefone: String,
    val cpf: String
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
            foto = R.drawable.robertofacundo
        )
    ),
    email = "pedrojoao1@email.com",
    telefone = "(88) 9 XXXX - XXXX",
    cpf = "XXX.XXX.XXX-XX"
)