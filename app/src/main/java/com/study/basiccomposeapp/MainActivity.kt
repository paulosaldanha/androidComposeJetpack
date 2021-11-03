package com.study.basiccomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.study.basiccomposeapp.handler.BackPressHandler
import com.study.basiccomposeapp.handler.LocalBackPressedDispatcher
import com.study.basiccomposeapp.ui.TopBar
import com.study.basiccomposeapp.ui.screens.*
import com.study.basiccomposeapp.ui.theme.BasicComposeAppTheme
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.composable
import com.study.basiccomposeapp.ui.screens.pais.Pais
import com.study.basiccomposeapp.ui.screens.estado.Estado
import com.study.basiccomposeapp.ui.screens.home.Home

class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicComposeAppTheme {
                //Disponibiliza o dispathcer para o AppScaffold e para todos os outros elementos composable que estão abaixo na hierarquia
               CompositionLocalProvider(LocalBackPressedDispatcher provides this.onBackPressedDispatcher) {
                   AppScaffold()
               }
            }
        }
    }

    @Composable
    fun AppScaffold() {
        val viewModel: MainViewModel = viewModel()
        val navController = rememberNavController()
        //ScaffoldState contem estado basico da aplicação
        // Scaffold permite que seja implementado a UI com o layout basico do Material Design
        // tal como TopAppBar, BottomAppBar, FloatingActionButton, e Drawer.
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        val currentScreen by viewModel.currentScreen.observeAsState()

        if (scaffoldState.drawerState.isOpen) {
            BackPressHandler {
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        }

        var topBar : @Composable () -> Unit = {
            TopBar(
                title = currentScreen!!.title,
                buttonIcon = Icons.Filled.Menu,
                onButtonClicked = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        }
        if (currentScreen == Screens.DrawerScreens.Estado) {
            topBar = {
                TopBar(
                    title = Screens.DrawerScreens.Estado.title,
                    buttonIcon = Icons.Filled.ArrowBack,
                    onButtonClicked = {
                        navController.popBackStack()
                    }
                )
            }
        }

        Scaffold(
            topBar = {
                topBar()
            },

            scaffoldState = scaffoldState,
            drawerContent = {
                Drawer { route ->
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    navController.navigate(route) {
                        //popUpTo = navController.graph.startDestinationRoute
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {

                                saveState = true
                            }
                        }
                        launchSingleTop = true
                    }
                }
            },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        ) { _ ->
            NavigationHost(navController = navController, viewModel = viewModel)
        }
    }

    @Composable
    fun NavigationHost(navController: NavController, viewModel: MainViewModel) {
        NavHost(
            navController = navController as NavHostController,
            startDestination = Screens.DrawerScreens.Home.route
        ) {
            composable(Screens.DrawerScreens.Home.route) {
                viewModel.setCurrentScreen(Screens.DrawerScreens.Home)
                Home(/*viewModel = viewModel*/)
            }
            composable(Screens.DrawerScreens.Pais.route) {
                viewModel.setCurrentScreen(Screens.DrawerScreens.Pais)
                Pais(/*viewModel = viewModel*/)
            }
            composable(Screens.DrawerScreens.Estado.route) {
                viewModel.setCurrentScreen(Screens.DrawerScreens.Estado)
                Estado(/*viewModel = viewModel*/)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultAppScaffold() {
        BasicComposeAppTheme {
            AppScaffold()
        }
    }
}
