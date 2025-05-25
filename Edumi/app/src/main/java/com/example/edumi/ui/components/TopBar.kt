package com.example.edumi.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessHigh
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@ExperimentalMaterial3Api
@Composable
fun TopBar(
    onThemeToggle: () -> Unit,
    onOpenDrawer: () -> Unit,
    onSearchClick: () -> Unit
) {
    TopAppBar(
        title = { Text("Edumi") },
        navigationIcon = {
            IconButton(onClick = onOpenDrawer) { // Abrir o menu ao clicar no ícone
                Icon(Icons.Default.Menu, contentDescription = "Abrir menu")
            }
        },
        actions = {
            IconButton(onClick = onSearchClick) { // Abrir a pesquisa
                Icon(Icons.Default.Search, contentDescription = "Pesquisar")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.MoreVert, contentDescription = "Opções")
            }
        }
    )
}