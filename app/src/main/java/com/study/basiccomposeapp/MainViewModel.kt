package com.study.basiccomposeapp


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.basiccomposeapp.ui.screens.Screens

class MainViewModel : ViewModel() {
    private  val _currentScreen = MutableLiveData<Screens>(Screens.DrawerScreens.Home)
    val currentScreen : LiveData<Screens> = _currentScreen

    fun setCurrentScreen(screen: Screens){
        _currentScreen.value = screen
    }

    private val _clickCount = MutableLiveData(0)
    val clickCount: LiveData<Int> = _clickCount

    fun updateClick(value: Int){
        _clickCount.value = value
    }
}