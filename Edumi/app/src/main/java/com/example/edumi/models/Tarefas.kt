package com.example.edumi.models

import java.time.LocalDate

data class Tarefas(
    val idFilho: Int,
    val titulo: String,
    val data: LocalDate,
    val descricao: String
)

val tasks = listOf(
    Tarefas(
        idFilho = 1,
        titulo = "Atividade de Matemática",
        data = LocalDate.of(2025, 5, 30),
        descricao = "Fazer os exercícios da página 35 questões 1, 2 e 3"
    ),
    Tarefas(
        idFilho = 1,
        titulo = "Leitura de Português",
        data = LocalDate.of(2025, 6, 5),
        descricao = "Ler o capítulo 3 do livro 'O Pequeno Príncipe'"
    ),
    Tarefas(
        idFilho = 1,
        titulo = "Pesquisa de Ciências",
        data = LocalDate.of(2025, 6, 10),
        descricao = "Pesquisar sobre o ciclo da água e fazer um resumo"
    ),
    Tarefas(
        idFilho = 1,
        titulo = "Desenho Livre",
        data = LocalDate.of(2025, 6, 15),
        descricao = "Desenhar algo que você goste por 30 minutos"
    ),
    Tarefas(
        idFilho = 1,
        titulo = "Leitura de Livro",
        data = LocalDate.of(2025, 6,20),
        descricao = "Ler Guerra e Paz inteiro sem pausas"
    ),
    Tarefas(
        idFilho = 2,
        titulo = "Trabalho de História",
        data = LocalDate.of(2025, 5, 28),
        descricao = "Preparar apresentação sobre a Revolução Industrial"
    ),
    Tarefas(
        idFilho = 2,
        titulo = "Estudar Inglês",
        data = LocalDate.of(2025, 6, 2),
        descricao = "Revisar vocabulário da unidade 4 e fazer exercícios"
    ),
    Tarefas(
        idFilho = 2,
        titulo = "Praticar Violão",
        data = LocalDate.of(2025, 6, 7),
        descricao = "Praticar a música 'Parabéns a Você' por 20 minutos"
    ),
)