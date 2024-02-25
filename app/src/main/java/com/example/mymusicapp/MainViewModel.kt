package com.example.mymusicapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    // Just want to Hold the current screen
    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.DrawerScreen.AddAccount)

    //Now we are going to have a exposable variable
    val currentScreen: MutableState<Screen>
        get() = _currentScreen

    //We want to set the current screen

    fun setCurrentScreen(screen: Screen){
        _currentScreen.value =screen
    }
}