package com.example.edumi.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import com.example.edumi.ui.theme.EdumiAppBarTitleStyle

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
        "profile" -> "Perfil"
        else -> "Edumi"
    }

    TopAppBar(
        title = {
            Text(
                title,
                style = EdumiAppBarTitleStyle.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = if (showBackButton) {
            {
                IconButton(onClick = {navController.popBackStack()}) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
            }
        } else {
            {
                IconButton(onClick = onOpenDrawer) {
                    Icon(Icons.Default.Menu, contentDescription = "Abrir menu")
                }
            }
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Default.Search, contentDescription = "Pesquisar")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.MoreVert, contentDescription = "Opções")
            }
        }
    )
}