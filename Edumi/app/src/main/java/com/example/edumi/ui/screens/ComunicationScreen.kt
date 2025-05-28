package com.example.edumi.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.edumi.models.comunicados
import com.example.edumi.models.resp

@Composable
fun AllNotifications(navController: NavHostController, context: Context) {
    val allComunicados = remember { comunicados }

    val allFilhos = remember { resp.filhos }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Todos os Comunicados",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            if (allComunicados.isEmpty()) {
                item {
                    Text("Nenhum comunicado disponível.")
                }
            } else {
                items(allComunicados.size) { index ->
                    val comunicado = allComunicados[index]
                    val nomeDoFilho = allFilhos.find { it.id == comunicado.idFilho }?.name ?: "Filho Desconhecido"

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "${comunicado.tipo} (${nomeDoFilho})",
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = comunicado.texto,
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                }
            }
        }
    }
}