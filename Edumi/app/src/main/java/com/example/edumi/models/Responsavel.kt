package com.example.edumi.models

import com.example.edumi.R

data class Responsavel(
    val id: String,
    val name: String,
    val email: String,
    val telefone: String,
    val sexo: String,
    val pais: String

) {
    companion object {
        fun empty() = Responsavel("", "", "", "", "", "")
    }
}