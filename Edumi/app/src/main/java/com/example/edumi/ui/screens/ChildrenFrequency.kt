package com.example.edumi.ui.screens

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.edumi.models.Filho
import com.example.edumi.models.frequencias
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun ChildrenFrequency(navController: NavController, context: Context, filho: Filho) {
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(2000)
        isLoading = false
    }

    val frequenciasFilho = remember { frequencias.filter { it.idFilho == filho.id } }
    val initialPage = 500
    val pagerState = rememberPagerState(initialPage = initialPage)

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
        label = "Loading or frequency"
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
                // Título principal
                Text(
                    text = "Frequência de ${filho.name}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                HorizontalPager(
                    count = Int.MAX_VALUE,
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth().weight(1f)
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

                    val frequenciasDoMes = frequenciasFilho.filter { YearMonth.from(it.data) == currentMonth }
                    val totalPresencas = frequenciasDoMes.count { it.presente }
                    val totalFaltas = frequenciasDoMes.count { !it.presente }
                    val totalAulasRegistradas = frequenciasDoMes.size
                    val percentualFaltas = if (totalAulasRegistradas > 0) {
                        (totalFaltas.toDouble() / totalAulasRegistradas * 100).roundToInt()
                    } else 0

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale("pt", "BR"))} ${currentMonth.year}",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val diasSemana = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb")
                            diasSemana.forEach {
                                Text(
                                    text = it,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.labelMedium,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(7),
                            modifier = Modifier.height(260.dp)
                        ) {
                            items(days) { (date, isFromOtherMonth) ->
                                val freqDoDia = frequenciasFilho.find { it.data == date }

                                val dayColor = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = if (isFromOtherMonth) 0.3f else 1f
                                )

                                val presenceColor = when {
                                    freqDoDia?.presente == true -> Color(0xFF4CAF50)
                                    freqDoDia?.presente == false -> Color(0xFFF44336)
                                    else -> null
                                }?.copy(alpha = if (isFromOtherMonth) 0.3f else 1f)

                                Box(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .aspectRatio(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = date.dayOfMonth.toString(),
                                            color = dayColor,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        presenceColor?.let { cor ->
                                            Box(
                                                modifier = Modifier
                                                    .size(6.dp)
                                                    .padding(top = 2.dp)
                                                    .background(color = cor, shape = CircleShape)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(6.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Resumo do mês", style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Presenças: $totalPresencas", color = Color(0xFF4CAF50))
                                Text("Faltas: $totalFaltas", color = Color(0xFFF44336))
                                Text(
                                    "Faltou $percentualFaltas% das aulas de ${
                                        currentMonth.month.getDisplayName(TextStyle.FULL, Locale("pt", "BR"))
                                    }"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
