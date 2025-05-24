package com.example.edumi.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
        // Seção principal com a lista de todos os eventos
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            item {
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
                                painter = painterResource(id = resp.filhos[0].foto),
                                contentDescription = resp.filhos[0].name,
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
                                    text = resp.filhos[0].name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Idade: ${resp.filhos[0].idade}",
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        // Descrição do evento
                        Text(
                            text = "Escola: ${resp.filhos[0].escola}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Botão "Ver mais sobre"
                        Button(
                            onClick = {
                                // navController.navigate()
                            },
                            modifier =
                                Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Ver mais informações a respeito do(a) ${resp.filhos[0].name}"
                            )
                        }
                    }
                }
            }
        }
    }
}