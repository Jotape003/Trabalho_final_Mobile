package com.example.edumi.ui.screens

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.edumi.models.Filho
import com.example.edumi.models.eventos
import com.example.edumi.models.resp
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AllChildrenEvents(navController: NavController, context: Context) {
    val todosOsFilhos = remember { resp.filhos }

    val todosOsEventosDosFilhos = remember {
        eventos.filter { evento ->
            todosOsFilhos.any { filho -> filho.id == evento.idFilho }
        }
    }

    val today = remember { LocalDate.now() }
    var selectedDate by remember { mutableStateOf(today) }

    val currentMonth = remember { YearMonth.from(today) }
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek.value % 7

    val days = remember {
        buildList {
            repeat(firstDayOfMonth) { add(null) }
            repeat(daysInMonth) { add(currentMonth.atDay(it + 1)) }
        }
    }
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(2000) // Simula delay
        isLoading = false
    }
    AnimatedContent(
        targetState = isLoading,
        transitionSpec = {
            (fadeIn() + expandVertically()) with (fadeOut() + shrinkVertically())
        },
        label = "Loading or Scores"
    ) { loading ->
        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Eventos em ${
                        currentMonth.month.getDisplayName(
                            TextStyle.FULL,
                            Locale("pt", "BR")
                        )
                    }",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.height(300.dp)
                ) {
                    items(days) { date ->
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .aspectRatio(1f)
                                .clickable(enabled = date != null) {
                                    if (date != null) selectedDate = date
                                }
                                .background(
                                    if (date == selectedDate) MaterialTheme.colorScheme.primary.copy(
                                        alpha = 0.3f
                                    ) else Color.Transparent
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = date?.dayOfMonth?.toString() ?: "",
                                    fontWeight = if (date == selectedDate) FontWeight.Bold else FontWeight.Normal
                                )

                                if (date != null && todosOsEventosDosFilhos.any { it.data == date }) {
                                    Box(
                                        modifier = Modifier
                                            .size(5.dp)
                                            .background(
                                                Color.Blue,
                                                shape = MaterialTheme.shapes.small
                                            )
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedContent(
                    targetState = selectedDate,
                    transitionSpec = {
                        fadeIn() + expandVertically() with fadeOut() + shrinkVertically()
                    },
                    label = "Data selecionada"
                ) { date ->
                    Column {
                        Text(
                            text = "Eventos em ${date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        LazyColumn {
                            val eventosDoDia = todosOsEventosDosFilhos.filter { it.data == date }

                            if (eventosDoDia.isEmpty()) {
                                item {
                                    Text("Nenhum evento para este dia.")
                                }
                            } else {
                                items(eventosDoDia.size) { index ->
                                    val evento = eventosDoDia[index]
                                    val nomeDoFilho =
                                        todosOsFilhos.find { it.id == evento.idFilho }?.name
                                            ?: "Filho Desconhecido"

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        elevation = CardDefaults.cardElevation(4.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(8.dp)) {
                                            Text(
                                                text = "${evento.titulo} (${nomeDoFilho})",
                                                style = MaterialTheme.typography.titleSmall
                                            )
                                            Text(text = "${evento.horaInicio} - ${evento.horaFim}")
                                            Text(text = evento.local)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}