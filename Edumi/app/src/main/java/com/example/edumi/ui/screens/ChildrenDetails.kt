package com.example.edumi.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.edumi.models.Filho
import kotlinx.coroutines.delay

@Composable
fun ChildrenDetails(navController: NavHostController, context: Context, filho: Filho) {
    Column {
        // Atividade dos filhos
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            item {

                Text(
                    text = "Dados do ${filho.name}",
                    style = MaterialTheme.typography.titleMedium
                )


                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        navController.navigate("ChildrenScores/${filho.id}")
                    },

                    modifier =
                        Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Ver notas"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        navController.navigate("ChildrenFrequency/${filho.id}")
                    },
                    modifier =
                        Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Ver frequência"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        navController.navigate("ChildrenEvents/${filho.id}")
                    },
                    modifier =
                        Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Ver eventos"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        navController.navigate("ChildrenNotifications/${filho.id}")
                    },
                    modifier =
                        Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Ver comunicados"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        navController.navigate("ChildrenTask/${filho.id}")
                    },
                    modifier =
                        Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Ver tarefas"
                    )
                }
            }

        }
    }
}
