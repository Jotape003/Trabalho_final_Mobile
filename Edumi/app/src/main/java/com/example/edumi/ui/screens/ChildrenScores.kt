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
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.edumi.models.Filho
import com.example.edumi.viewmodel.NotaViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChildrenScores(
    navController: NavController,
    context: Context, filho: Filho,
    viewModel: NotaViewModel = viewModel()
) {
    var isLoading by remember { mutableStateOf(true) }
    val notas = viewModel.notas
    val notasFiltradas = notas.filter { it.idFilho == filho.id }

    LaunchedEffect(Unit) {
        delay(2000)
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
                    text = "Notas de ${filho.name}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                ) {
                    Text(
                        text = "Disciplina",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = "Semestre",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = "Nota",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            textAlign = TextAlign.Center
                        )
                    )
                }

                notasFiltradas.forEachIndexed { index, nota ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                    ) {
                        Text(
                            text = nota.disciplina,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                textAlign = TextAlign.Center
                            )
                        )
                        Text(
                            text = nota.semestre,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                textAlign = TextAlign.Center
                            )
                        )
                        Text(
                            text = nota.nota.toString(),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                textAlign = TextAlign.Center
                            )
                        )
                    }

                    if (index != notas.lastIndex) {
                        Divider(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
    }
}
