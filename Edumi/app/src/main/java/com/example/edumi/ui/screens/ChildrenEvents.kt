package com.example.edumi.ui.screens

import android.content.Context
import androidx.compose.animation.*
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
import androidx.navigation.NavController
import com.example.edumi.models.Filho
import com.example.edumi.models.eventos
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun ChildrenEvents(navController: NavController, context: Context, filho: Filho) {
    var isLoading by remember { mutableStateOf(true) }
    val eventosFilho = remember { eventos.filter { it.idFilho == filho.id } }
    val initialPage = 500
    val pagerState = rememberPagerState(initialPage = initialPage)
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    fun indexToYearMonth(index: Int): YearMonth {
        val current = YearMonth.now()
        val offset = index - initialPage
        return current.plusMonths(offset.toLong())
    }

    LaunchedEffect(Unit) {
        delay(2000)
        isLoading = false
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
                    text = "Eventos de ${filho.name}",
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
                    val daysInMonth = currentMonth.lengthOfMonth()
                    val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek.value % 7

                    val days = buildList {
                        repeat(firstDayOfMonth) { add(null) }
                        repeat(daysInMonth) { add(currentMonth.atDay(it + 1)) }
                    }

                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale("pt", "BR"))} ${currentMonth.year}",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(7),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            items(days) { date ->
                                val hasEvent = date != null && eventosFilho.any { it.data == date }
                                val isSelected = date == selectedDate

                                Box(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .aspectRatio(1f)
                                        .clickable(enabled = date != null) {
                                            if (date != null) selectedDate = date
                                        }
                                        .background(
                                            if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                            else Color.Transparent
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = date?.dayOfMonth?.toString() ?: "",
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                        if (hasEvent) {
                                            Box(
                                                modifier = Modifier
                                                    .size(5.dp)
                                                    .padding(top = 2.dp)
                                                    .background(
                                                        Color.Blue,
                                                        shape = CircleShape
                                                    )
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        val eventosDoDia = eventosFilho.filter { it.data == selectedDate }

                        Column(modifier = Modifier.fillMaxWidth()) {
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
                                        val evento = eventosDoDia[index]
                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            elevation = CardDefaults.cardElevation(4.dp)
                                        ) {
                                            Column(modifier = Modifier.padding(8.dp)) {
                                                Text(
                                                    text = evento.titulo,
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
