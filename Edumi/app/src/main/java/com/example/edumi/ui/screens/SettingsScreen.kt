package com.example.edumi.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.edumi.models.eventos
import com.example.edumi.notifications.agendarNotificacaoEvento
import com.example.edumi.notifications.cancelarNotificacaoEvento

@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    isNotificationsEnabled: Boolean,
    onThemeToggle: () -> Unit,
    onNotificationsToggle: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 24.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .height(62.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.DarkMode, contentDescription = "Modo escuro")

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Modo escuro"
            )

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                checked = isDarkTheme,
                onCheckedChange = {
                    onThemeToggle()
                }
            )
        }

        Row(
            modifier = Modifier
                .height(62.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Notifications, contentDescription = "Notificações")

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Notificações"
            )

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                checked = !isNotificationsEnabled,
                onCheckedChange = {
                    onNotificationsToggle()
                }
            )
        }
    }
}