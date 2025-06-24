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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.edumi.models.Filho
import com.example.edumi.models.eventos
import com.example.edumi.models.frequencias
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChildrenFrequency(navController: NavController, context: Context, filho: Filho) {
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(2000) // Simula delay
        isLoading = false
    }

    val frequenciasFilho = remember { frequencias.filter { it.idFilho == filho.id } }
    val today = remember { LocalDate.now() }

    val currentMonth = remember { YearMonth.from(today) }
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek.value % 7 // 0 = Domingo

    val days = remember {
        buildList<LocalDate?> {
            repeat(firstDayOfMonth) { add(null) }
            repeat(daysInMonth) { add(currentMonth.atDay(it + 1)) }
        }
    }

    val frequenciasDoMes = frequenciasFilho.filter { YearMonth.from(it.data) == currentMonth }
    val totalPresencas = frequenciasDoMes.count { it.presente }
    val totalFaltas = frequenciasDoMes.count { !it.presente }
    val totalAulasRegistradas = frequenciasDoMes.size
    val percentualFaltas = if (totalAulasRegistradas > 0) {
        (totalFaltas.toDouble() / totalAulasRegistradas * 100).roundToInt()
    } else 0



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
                Text(
                    text = "Frequência de ${filho.name} em ${
                        currentMonth.month.getDisplayName(TextStyle.FULL, Locale("pt", "BR"))
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
                        val freqDoDia = frequenciasFilho.find { it.data == date }

                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .aspectRatio(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = date?.dayOfMonth?.toString() ?: "")
                                if (freqDoDia != null) {
                                    val cor =
                                        if (freqDoDia.presente) Color(0xFF4CAF50) else Color(
                                            0xFFF44336
                                        )
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

                // Resumo do mês
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Resumo do mês", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Presenças: ${totalPresencas}", color = Color(0xFF4CAF50))
                        Text("Faltas: ${totalFaltas}", color = Color(0xFFF44336))
                        Text(
                            "Faltou ${percentualFaltas}% das aulas de ${
                                currentMonth.month.getDisplayName(
                                    TextStyle.FULL,
                                    Locale("pt", "BR")
                                )
                            }"
                        )
                    }
                }
            }

        }
    }
}
