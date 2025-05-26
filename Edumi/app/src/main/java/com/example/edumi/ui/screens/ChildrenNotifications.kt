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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.edumi.models.Filho
import com.example.edumi.models.comunicados

@Composable
fun ChildrenNotifications(navController: NavHostController, context: Context, filho: Filho){

    // Filtrar comunicados apenas do filho atual
    val comunicadosDoFilho = comunicados.filter { it.idFilho == filho.id }

    Column (modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Comunicados de ${filho.name}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(comunicadosDoFilho.size) { index ->
                val comunicado = comunicadosDoFilho[index]

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = comunicado.tipo,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = comunicado.texto,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
