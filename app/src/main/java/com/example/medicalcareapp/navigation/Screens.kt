package com.example.medicalcareapp.navigation

sealed class Screens(
    val route: String,
) {
    object Splash: Screens("splash_screen")
    object Welcome: Screens("welcome")
    object Login: Screens("login")
    object Register : Screens("register")
}