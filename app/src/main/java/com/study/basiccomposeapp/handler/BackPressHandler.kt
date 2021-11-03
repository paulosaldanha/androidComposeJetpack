package com.study.basiccomposeapp.handler

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.*

@Composable
fun BackPressHandler(onBackPressed: () -> Unit){
    //atualiza onBack atual lambda quando um novo é criado
    val currentOnBackPressed by rememberUpdatedState(onBackPressed)
    //Guarda o call back anterior lambda
    val backCallback = remember {
        object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    val backDispatcher = LocalBackPressedDispatcher.current
    //Quando houver um novo dispatcher configura o callback
    DisposableEffect(backDispatcher){
        backDispatcher.addCallback(backCallback)
        //Quando o efeito deixar a composição ou tiver um novo dispatcher, remove o callback
        onDispose {
            backCallback.remove()
        }
    }
}

val LocalBackPressedDispatcher =  staticCompositionLocalOf<OnBackPressedDispatcher> { error("No Back Dispatcher provided") }