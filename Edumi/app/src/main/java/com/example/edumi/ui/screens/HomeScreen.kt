package com.example.edumi.ui.screens

import EscolaViewModel
import TurmaViewModel
import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.edumi.models.Filho
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    context: Context,
    filhos: List<Filho>,
    escolaViewModel: EscolaViewModel = viewModel(),
    turmaViewModel: TurmaViewModel = viewModel()
) {
    val nomesEscolas = remember { mutableStateMapOf<String, String>() }
    val nomesTurmas = remember { mutableStateMapOf<String, String>() }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(filhos) {
        escolaViewModel.ouvirEscolas()
        for (filho in filhos) {
            escolaViewModel.getNomeEscolaPorId(filho.idEscola) { nome ->
                nomesEscolas[filho.id] = nome
            }
            turmaViewModel.getNomeTurmaPorId(filho.idTurma) { nome ->
                nomesTurmas[filho.id] = nome
            }
        }

        while (filhos.any { filho ->
                nomesEscolas[filho.id].isNullOrBlank() || nomesTurmas[filho.id].isNullOrBlank()
            }) {
            delay(100)
        }

        isLoading = false
    }

    Crossfade(targetState = isLoading, label = "Loading") { loading ->
        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if (filhos.isEmpty()) {
                Text(
                    text = "Não há vinculos com você!",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 8.dp, bottom = 12.dp)
                )
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Seus vínculos",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .padding(top = 8.dp, bottom = 12.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filhos) { filho ->
                            val nomeEscola = nomesEscolas[filho.id] ?: "Escola desconhecida"
                            val nomeTurma = nomesTurmas[filho.id] ?: "Turma desconhecida"

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navHostController.navigate("ChildrenDetails/${filho.id}")
                                    },
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Spacer(modifier = Modifier.width(20.dp))

                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = filho.name,
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))

                                        Text(
                                            text = nomeEscola,
                                            style = MaterialTheme.typography.bodyMedium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))

                                        Text(
                                            text = "Idade: ${filho.idade}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Text(
                                            text = "Turma: $nomeTurma",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "Detalhes",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
