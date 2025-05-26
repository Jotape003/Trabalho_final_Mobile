package com.example.edumi.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.edumi.models.resp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .padding(end = 20.dp)
            .background(MaterialTheme.colorScheme.surface),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = resp.imageRes),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = resp.name,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = resp.email,
                    style = MaterialTheme.typography.titleSmall,
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(bottom = 6.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    Icons.Default.Person,
                    contentDescription = "Perfil do usuário"
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Perfil",
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable {
                        scope.launch {
                            drawerState.close()
                        }
                        navController.navigate("settings")
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Configurações"
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Configurações",
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    Icons.Default.Help,
                    contentDescription = "Ajuda"
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Ajuda",
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    Icons.Default.Logout,
                    contentDescription = "Sair",
                    tint = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Sair",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}