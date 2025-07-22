package com.example.edumi.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.edumi.models.Filho

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChildrenDetails(navController: NavHostController, filho: Filho) {

    val cards = listOf(
        CardInfo("Notas", Icons.Default.Grade) { navController.navigate("ChildrenScores/${filho.id}") },
        CardInfo("Frequência", Icons.Default.CalendarMonth) { navController.navigate("ChildrenFrequency/${filho.id}") },
        CardInfo("Eventos", Icons.Default.Event) { navController.navigate("ChildrenEvents/${filho.id}") },
        CardInfo("Comunicados", Icons.Default.Campaign) { navController.navigate("ChildrenNotifications/${filho.id}") },
        CardInfo("Atividades", Icons.Default.Checklist) { navController.navigate("ChildrenTask/${filho.id}") },
        CardInfo("Editar", Icons.Default.Edit) {navController.navigate("EditChild/${filho.id}")}
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Resumo escolar de ${filho.name}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(cards) { card ->
                InfoCard(title = card.title, icon = card.icon, onClick = card.onClick)
            }
        }
    }
}

data class CardInfo(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun InfoCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.TopStart)
            )

            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}
