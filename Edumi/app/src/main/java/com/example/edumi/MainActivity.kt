package com.example.edumi

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.edumi.datastore.Preferences
import com.example.edumi.models.resp
import com.example.edumi.ui.components.BottomNavigationBar
import com.example.edumi.ui.components.DrawerContent
import com.example.edumi.ui.components.TopBar
import com.example.edumi.ui.screens.AllChildrenEvents
import com.example.edumi.ui.screens.AllNotifications
import com.example.edumi.ui.screens.ChildrenDetails
import com.example.edumi.ui.screens.ChildrenEvents
import com.example.edumi.ui.screens.ChildrenFrequency
import com.example.edumi.ui.screens.ChildrenNotifications
import com.example.edumi.ui.screens.ChildrenScores
import com.example.edumi.ui.screens.ChildrenTask
import com.example.edumi.ui.screens.HelpScreen
import com.example.edumi.ui.screens.HomeScreen
import com.example.edumi.ui.screens.ProfileScreen
import com.example.edumi.ui.screens.SettingsScreen
import com.example.edumi.ui.theme.EdumiTheme
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.edumi.models.eventos
import com.example.edumi.notifications.agendarNotificacaoEvento
import com.example.edumi.notifications.cancelarNotificacaoEvento
import com.example.edumi.ui.screens.ChildForm
import com.example.edumi.ui.theme.PrimaryColorPairs
import java.time.LocalDateTime


@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Eventos"
            val descriptionText = "Um evento está próximo, cheque seu calendário!"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("EVENTOS_CHANNEL", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStackEntry?.destination?.route
            val preferences = remember { Preferences(context) }
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val isDarkTheme by preferences.isDarkMode.collectAsState(initial = false)
            val isNotificationsEnabled by preferences.isNotificationsEnabled.collectAsState(initial = false)
            val primaryColorName by preferences.primaryColorName.collectAsState(initial = "Azul")

            val primaryColorPair = PrimaryColorPairs.find { it.name == primaryColorName } ?: PrimaryColorPairs[0]

            EdumiTheme(
                darkTheme = isDarkTheme,
                primaryColorPair = primaryColorPair
            ) {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = true,
                    drawerContent = {
                        DrawerContent(navController, drawerState, scope)
                    },
                    content = {
                        Scaffold(
                            topBar = {
                                TopBar(
                                    onOpenDrawer = { scope.launch { drawerState.open() } },
                                    onSearchClick = {
                                        // TEM QUE CONFIGURAR BEM AQUI
                                    },
                                    navController = navController
                                )
                            },

                            bottomBar = { BottomNavigationBar(navController) },

                            floatingActionButton = {
                                if (currentRoute != "child-form") {
                                    FloatingActionButton(
                                        onClick = {
                                            navController.navigate("child-form")
                                        },
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        shape = CircleShape
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = "Adicionar")
                                    }
                                }
                            }

                        ) { innerPadding ->
                            NavHost(
                                navController = navController,
                                startDestination = "home",
                                modifier = Modifier.padding(innerPadding)

                            ) {
                                composable("home") {
                                    HomeScreen(
                                        navController,
                                        context = context
                                    )
                                }
                                composable("events") {
                                    AllChildrenEvents(
                                        navController = navController,
                                        context = context,
                                    )
                                }
                                composable("notice"){
                                    AllNotifications(
                                        navController = navController,
                                        context = context,
                                    )
                                }
                                composable("ChildrenDetails/{id}") { backStackEntry ->
                                    val id = backStackEntry.arguments?.getString("id")
                                    val filho = resp.filhos.find { it.id.toString() == id }

                                    if (filho != null) {
                                        ChildrenDetails(
                                            navController = navController,
                                            filho = filho
                                        )
                                    } else {
                                        Text("Filho não encontrado.")
                                    }
                                }
                                composable("ChildrenScores/{id}") { backStackEntry ->
                                    val id = backStackEntry.arguments?.getString("id")
                                    val filho = resp.filhos.find { it.id.toString() == id }

                                    if (filho != null) {
                                        ChildrenScores(
                                            navController = navController,
                                            context = context,
                                            filho = filho
                                        )
                                    } else {
                                        Text("Filho não encontrado.")
                                    }
                                }
                                composable("ChildrenNotifications/{id}") { backStackEntry ->
                                    val id = backStackEntry.arguments?.getString("id")
                                    val filho = resp.filhos.find { it.id.toString() == id }

                                    if (filho != null) {
                                        ChildrenNotifications(
                                            navController = navController,
                                            context = context,
                                            filho = filho
                                        )
                                    } else {
                                        Text("Filho não encontrado.")
                                    }
                                }
                                composable("ChildrenEvents/{id}") { backStackEntry ->
                                    val id = backStackEntry.arguments?.getString("id")
                                    val filho = resp.filhos.find { it.id.toString() == id }

                                    if (filho != null) {
                                        ChildrenEvents(
                                            navController = navController,
                                            context = context,
                                            filho = filho
                                        )
                                    } else {
                                        Text("Filho não encontrado.")
                                    }
                                }
                                composable("ChildrenFrequency/{id}") { backStackEntry ->
                                    val id = backStackEntry.arguments?.getString("id")
                                    val filho = resp.filhos.find { it.id.toString() == id }

                                    if (filho != null) {
                                        ChildrenFrequency(
                                            navController = navController,
                                            context = context,
                                            filho = filho
                                        )
                                    } else {
                                        Text("Filho não encontrado.")
                                    }

                                }
                                composable("ChildrenTask/{id}") { backStackEntry ->
                                    val id = backStackEntry.arguments?.getString("id")
                                    val filho = resp.filhos.find { it.id.toString() == id }

                                    if (filho != null) {
                                        ChildrenTask(
                                            navController = navController,
                                            context = context,
                                            filho = filho
                                        )
                                    } else {
                                        Text("Filho não encontrado.")
                                    }

                                }
                                composable("settings") {
                                    SettingsScreen(
                                        isDarkTheme = isDarkTheme,
                                        isNotificationsEnabled = isNotificationsEnabled,
                                        primaryColorPair = primaryColorPair,
                                        onThemeToggle = {
                                            scope.launch {
                                                preferences.setDarkMode(!isDarkTheme)
                                            }
                                        },
                                        onNotificationsToggle = {
                                            scope.launch {
                                                val novoEstado = !isNotificationsEnabled
                                                preferences.setNotificationsEnabled(novoEstado)

                                                if (novoEstado) {
                                                    eventos
                                                        .filter {
                                                            LocalDateTime.of(it.data, it.horaInicio).isAfter(LocalDateTime.now())
                                                        }
                                                        .forEach { evento ->
                                                            agendarNotificacaoEvento(context, evento)
                                                        }
                                                } else {
                                                    eventos.forEach { evento ->
                                                        cancelarNotificacaoEvento(context, evento)
                                                    }
                                                }
                                            }
                                        }
                                        ,
                                        onPrimaryColorSelected = { newColorPair ->
                                            scope.launch {
                                                preferences.setPrimaryColorName(newColorPair.name)
                                            }
                                        }
                                    )
                                }

                                composable("help") {
                                    HelpScreen()
                                }

                                composable("profile") {
                                    ProfileScreen()
                                }

                                composable("child-form") {
                                    ChildForm(navController, {})
                                }
                            }
                        }
                    })
            }
        }
    }
}