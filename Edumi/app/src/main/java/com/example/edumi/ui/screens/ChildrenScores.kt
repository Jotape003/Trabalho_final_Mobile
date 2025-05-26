package com.example.edumi.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.edumi.models.Filho
import com.example.edumi.models.notas

@Composable
fun ChildrenScores(navController: NavController, context: Context, filho: Filho) {

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Notas de ${filho.name}",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Cabeçalho da tabela
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Text(text = "Disciplina", modifier = Modifier.weight(1f))
            Text(text = "Semestre", modifier = Modifier.weight(1f))
            Text(text = "Nota", modifier = Modifier.weight(1f))
        }

        // Linhas da tabela
        notas.forEach { nota ->
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(text = nota.disciplina, modifier = Modifier.weight(1f))
                Text(text = nota.semestre, modifier = Modifier.weight(1f))
                Text(text = nota.nota.toString(), modifier = Modifier.weight(1f))
            }
        }
    }
}