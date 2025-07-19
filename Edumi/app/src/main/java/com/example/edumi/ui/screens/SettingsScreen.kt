package com.example.edumi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.edumi.models.eventos
import com.example.edumi.notifications.agendarNotificacaoEvento
import com.example.edumi.notifications.cancelarNotificacaoEvento
import com.example.edumi.ui.theme.PrimaryColorPair
import com.example.edumi.ui.theme.PrimaryColorPairs


@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    isNotificationsEnabled: Boolean,
    primaryColorPair: PrimaryColorPair,
    onThemeToggle: () -> Unit,
    onNotificationsToggle: () -> Unit,
    onPrimaryColorSelected: (PrimaryColorPair) -> Unit
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
                checked = isNotificationsEnabled,
                onCheckedChange = {
                    onNotificationsToggle()
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Tema", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            PrimaryColorPairs.forEach { colorPair ->
                val isSelected = colorPair == primaryColorPair
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = if (isDarkTheme) colorPair.dark else colorPair.light,
                            shape = CircleShape
                        )
                        .clickable {
                            onPrimaryColorSelected(colorPair)
                        }
                        .then(
                            if (isSelected) Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                                    shape = CircleShape
                                )
                            else Modifier
                        )
                )
            }
        }
    }
}