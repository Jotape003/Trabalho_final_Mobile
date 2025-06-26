package com.example.edumi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val isDarkTheme = remember { mutableStateOf(false) }
            EdumiTheme(darkTheme = isDarkTheme.value) {
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

                            bottomBar = { BottomNavigationBar(navController) }

                        ) { innerPadding ->
                            NavHost(
                                navController = navController,
                                startDestination = "home",
                                modifier = Modifier.padding(innerPadding)

                            ) {
                                composable("home") {
                                    HomeScreen(
                                        navController,
                                        context = LocalContext.current
                                    )
                                }
                                composable("events") {
                                    AllChildrenEvents(
                                        navController = navController,
                                        context = LocalContext.current,
                                    )
                                }
                                composable("notice"){
                                    AllNotifications(
                                        navController = navController,
                                        context = LocalContext.current,
                                    )
                                }
                                composable("ChildrenDetails/{id}") { backStackEntry ->
                                    val id = backStackEntry.arguments?.getString("id")
                                    val filho = resp.filhos.find { it.id.toString() == id }

                                    if (filho != null) {
                                        ChildrenDetails(
                                            navController = navController,
                                            context = LocalContext.current,
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
                                            context = LocalContext.current,
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
                                            context = LocalContext.current,
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
                                            context = LocalContext.current,
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
                                            context = LocalContext.current,
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
                                            context = LocalContext.current,
                                            filho = filho
                                        )
                                    } else {
                                        Text("Filho não encontrado.")
                                    }

                                }
                                composable("settings") {
                                    SettingsScreen(
                                        onThemeToggle = {
                                            isDarkTheme.value = !isDarkTheme.value
                                        }
                                    )
                                }

                                composable("help") {
                                    HelpScreen()
                                }

                                composable("profile") {
                                    ProfileScreen()
                                }
                                //composable("events") { SubscribedEventsScreen(navController) }
                                //composable("favorites") { FavoritesScreen(navController) }
                                //composable("eventDetails/{eventId}") { backStackEntry ->
                                //   val eventId = backStackEntry.arguments?.getString("eventId")
                                // EventDetailsScreen(eventId = eventId)
                                //}
                            }
                        }
                    })
            }
        }
    }
}