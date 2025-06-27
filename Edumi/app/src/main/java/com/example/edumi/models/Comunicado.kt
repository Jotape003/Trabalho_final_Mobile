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
        texto = "Prova de matemática será aplicada no dia 10/06. Revisar exercícios do capítulo 3."
    ),
    Comunicado(
        idFilho = 1,
        tipo = "Evento",
        texto = "Passeio escolar ao museu será no dia 20/06. Enviar autorização assinada."
    ),
    Comunicado(
        idFilho = 1,
        tipo = "Aviso",
        texto = "Não haverá aula na sexta-feira, dia 12/06, devido ao feriado."
    ),
    Comunicado(
        idFilho = 1,
        tipo = "Lembrete",
        texto = "Trazer material esportivo para a aula de educação física na próxima terça."
    ),
    Comunicado(
        idFilho = 2,
        tipo = "Reunião",
        texto = "Reunião do conselho escolar dia 22/06 às 19h na biblioteca."
    ),
    Comunicado(
        idFilho = 2,
        tipo = "Lembrete",
        texto = "Entrega do projeto de ciências até o dia 18/06. Preparar apresentação."
    ),
    Comunicado(
        idFilho = 2,
        tipo = "Evento",
        texto = "Festival cultural no dia 25/06. Confirmar presença com o professor."
    ),
    Comunicado(
        idFilho = 2,
        tipo = "Aviso",
        texto = "Mudança no horário das aulas na próxima semana. Verificar o novo cronograma."
    ),
    Comunicado(
        idFilho = 2,
        tipo = "Lembrete",
        texto = "Levar lanche saudável para o piquenique do dia 30/06."
    )
)
