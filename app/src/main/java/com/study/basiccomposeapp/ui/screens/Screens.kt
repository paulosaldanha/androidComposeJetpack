package com.study.basiccomposeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.study.basiccomposeapp.MainViewModel

sealed class Screens(val route: String, val title: String){
    sealed class DrawerScreens(route: String, title: String) : Screens(route,title){
        object Home : DrawerScreens("home","Home")
        object Pais : DrawerScreens("pais","Pais")
        object Estado : DrawerScreens("estado","Estado")
    }
}

val screensFromDrawer = listOf(
    Screens.DrawerScreens.Home,
    Screens.DrawerScreens.Pais,
    Screens.DrawerScreens.Estado,
)




