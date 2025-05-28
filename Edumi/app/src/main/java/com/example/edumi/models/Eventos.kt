package com.example.edumi.models

import java.time.LocalDate
import java.time.LocalTime

data class Evento(
    val idFilho: Int,
    val titulo: String,
    val data: LocalDate,
    val horaInicio: LocalTime,
    val horaFim: LocalTime,
    val local: String
)


val eventos = listOf(
    Evento(1, "Reunião com Professores", LocalDate.of(2025, 5, 28), LocalTime.of(9, 0), LocalTime.of(10, 0), "Sala de Reuniões"),
    Evento(1, "Feira de Ciências", LocalDate.of(2025, 5, 28), LocalTime.of(13, 0), LocalTime.of(17, 0), "Quadra"),
    Evento(1, "Apresentação de Teatro", LocalDate.of(2025, 5, 30), LocalTime.of(10, 0), LocalTime.of(11, 30), "Auditório"),
    Evento(2, "Reunião com Tias", LocalDate.of(2025, 5, 2), LocalTime.of(9, 0), LocalTime.of(10, 0), "Sala de Reuniões"),
    Evento(2, "Feira de Matemática", LocalDate.of(2025, 5, 10), LocalTime.of(13, 0), LocalTime.of(17, 0), "Quadra"),
    Evento(2, "Apresentação de Cinema", LocalDate.of(2025, 5, 28), LocalTime.of(10, 0), LocalTime.of(11, 30), "Auditório")
)
