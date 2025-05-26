package com.example.edumi.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue


@ExperimentalMaterial3Api
@Composable
fun TopBar(
    navController: NavController,
    onOpenDrawer: () -> Unit,
    onSearchClick: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBackButton = currentRoute != "home"
    val title = when (currentRoute) {
        "home" -> "Edumi"
        "settings" -> "Configurações"
        "help" -> "Ajuda"
        else -> "Edumi"
    }

    TopAppBar(
        title = { Text(title) },
        navigationIcon = if (showBackButton) {
            {
                IconButton(onClick = {navController.popBackStack()}) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
            }
        } else {
            {
                IconButton(onClick = onOpenDrawer) { // Abrir o menu ao clicar no ícone
                    Icon(Icons.Default.Menu, contentDescription = "Abrir menu")
                }
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