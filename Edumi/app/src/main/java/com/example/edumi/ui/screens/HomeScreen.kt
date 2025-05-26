package com.example.edumi.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.edumi.models.resp


@Composable
fun HomeScreen(navController: NavHostController, context: Context) {
    Column {
        // Lista de filhos
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(resp.filhos) { filho ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {},
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = filho.foto),
                                contentDescription = filho.name,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = filho.name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Idade: ${filho.idade}",
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        // Descrição do evento
                        Text(
                            text = "Escola: ${filho.escola}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Botão "Ver mais sobre"
                        Button(
                            onClick = {
                                 navController.navigate("ChildrenDetails/${filho.id}")
                            },
                            modifier =
                                Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Ver mais informações a respeito do(a) ${filho.name}"
                            )
                        }
                    }
                }
            }
        }
    }
}