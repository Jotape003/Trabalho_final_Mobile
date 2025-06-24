package com.example.edumi.ui.screens

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.edumi.models.Filho
import com.example.edumi.models.notas
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChildrenScores(navController: NavController, context: Context, filho: Filho) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000) // Simula um carregamento
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
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {

                Text(
                    text = "Notas de ${filho.name}",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Cabeçalho da "tabela"
                Row(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(text = "Disciplina", modifier = Modifier.weight(1f))
                    Text(text = "Semestre", modifier = Modifier.weight(1f))
                    Text(text = "Nota", modifier = Modifier.weight(1f))
                }

                // Linhas da "tabela"
                notas.forEach { nota ->
                    Row(modifier = Modifier.padding(vertical = 4.dp)) {
                        Text(text = nota.disciplina, modifier = Modifier.weight(1f))
                        Text(text = nota.semestre, modifier = Modifier.weight(1f))
                        Text(text = nota.nota.toString(), modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
