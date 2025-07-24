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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.edumi.models.Filho
import com.google.accompanist.pager.*
import com.example.edumi.viewmodel.EventoViewModel
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun AllChildrenEvents(
    navController: NavController,
    context: Context,
    viewModel: EventoViewModel = viewModel(),
    filhos: List<Filho>
) {
    var isLoading = viewModel.isLoading
    var eventos = viewModel.eventos

    LaunchedEffect(filhos) {
        // A chamada agora passa o contexto para o agendamento de notificações
        viewModel.carregarEventos(filhos)
    }

    val initialPage = 500
    val pagerState = rememberPagerState(initialPage = initialPage)
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")


    fun indexToYearMonth(index: Int): YearMonth {
        val current = YearMonth.now()
        val offset = index - initialPage
        return current.plusMonths(offset.toLong())
    }


    AnimatedContent(
        targetState = isLoading,
        transitionSpec = {
            (fadeIn() + expandVertically()) with (fadeOut() + shrinkVertically())
        },
        label = "Loading or Events"
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
                    text = "Calendário de Eventos",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                HorizontalPager(
                    count = Int.MAX_VALUE,
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                ) { page ->
                    val currentMonth = indexToYearMonth(page)

                    val days = remember(currentMonth) {
                        val previousMonth = currentMonth.minusMonths(1)
                        val nextMonth = currentMonth.plusMonths(1)

                        val daysInMonth = currentMonth.lengthOfMonth()
                        val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek.value % 7
                        val daysBefore = firstDayOfMonth
                        val daysAfter = (7 - ((daysInMonth + daysBefore) % 7)).let { if (it == 7) 0 else it }

                        val previousMonthDays = (previousMonth.lengthOfMonth() - daysBefore + 1..previousMonth.lengthOfMonth()).map {
                            previousMonth.atDay(it) to true
                        }
                        val currentMonthDays = (1..daysInMonth).map {
                            currentMonth.atDay(it) to false
                        }
                        val nextMonthDays = (1..daysAfter).map {
                            nextMonth.atDay(it) to true
                        }

                        previousMonthDays + currentMonthDays + nextMonthDays
                    }

                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale("pt", "BR"))} ${currentMonth.year}",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val diasSemana = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb")
                            diasSemana.forEach { dia ->
                                Text(
                                    text = dia,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.labelMedium,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(7),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(days) { (date, isFromOtherMonth) ->
                                val isSelected = date == selectedDate
                                val hasEvent = eventos.any { (_, evento) ->
                                    LocalDate.parse(evento.data, formatter) == date
                                }


                                Box(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .aspectRatio(1f)
                                        .clickable { selectedDate = date }
                                        .background(
                                            if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                            else Color.Transparent
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = date.dayOfMonth.toString(),
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (!isFromOtherMonth) MaterialTheme.colorScheme.onBackground
                                            else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                                        )
                                        if (hasEvent) {
                                            Box(
                                                modifier = Modifier
                                                    .size(5.dp)
                                                    .padding(top = 2.dp)
                                                    .background(
                                                        if (!isFromOtherMonth) Color.Blue else Color.Blue.copy(alpha = 0.4f),
                                                        shape = CircleShape
                                                    )
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        val eventosDoDia = eventos.filter { (_, evento) ->
                            LocalDate.parse(evento.data, formatter) == selectedDate
                        }

                        Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                            Text(
                                text = "Eventos de ${selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}",
                                style = MaterialTheme.typography.titleMedium
                            )

                            if (eventosDoDia.isEmpty()) {
                                Text("Nenhum evento.")
                            } else {
                                Spacer(modifier = Modifier.height(4.dp))
                                LazyColumn {
                                    items(eventosDoDia.size) { index ->
                                        val (_, evento) = eventosDoDia[index]

                                        val nomeDoFilho = filhos.find { it.id == evento.idFilho }?.name ?: "Filho Desconhecido"

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
}
