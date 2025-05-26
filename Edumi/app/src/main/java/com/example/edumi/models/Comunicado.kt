package com.example.edumi.models

data class Comunicado(
    val idFilho: Int,
    val tipo : String,
    val texto: String,
)

val comunicados = listOf(
    Comunicado(
        idFilho = 1,
        tipo = "Reunião",
        texto = "Reunião de pais marcada para o dia 15/06 às 18h na escola."
    ),
    Comunicado(
        idFilho = 1,
        tipo = "Lembrete",
        texto = "Prova de matemática será aplicada no dia 10/06."
    ),
    Comunicado(
        idFilho = 1,
        tipo = "Evento",
        texto = "Passeio escolar será no dia 20/06. Enviar autorização."
    )
)
