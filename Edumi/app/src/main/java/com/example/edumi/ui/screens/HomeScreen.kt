package com.example.edumi.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.edumi.models.resp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button

@Composable
fun HomeScreen(navHostController: NavHostController, context: Context) {
    Column {
        Text(
            text = "Seus vínculos",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp)
        )
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(resp.filhos) { filho ->
                Card(
                    modifier = Modifier.padding(8.dp),
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
                                modifier = Modifier.size(64.dp).clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = filho.name,
                                    style = MaterialTheme.typography.titleMedium,

                                    )

                                Text(
                                    text = filho.escola,
                                    style = MaterialTheme.typography.titleSmall,
                                )

                                Text(
                                    text = filho.turma,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {},
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Ver mais")
                        }
                    }
                }
            }
        }
    }
}