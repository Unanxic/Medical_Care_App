package com.example.medicalcareapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medicalcareapp.screens.LoginScreen
import com.example.medicalcareapp.screens.RegisterScreen
import com.example.medicalcareapp.screens.WelcomeScreen


@Composable
fun MainNavController(
) {
    val navController = rememberNavController()
    var currentScreen by remember { mutableStateOf<Screens>(Screens.Welcome) }


    Box(Modifier.fillMaxSize()) {
        NavHost(
            modifier = Modifier
                .fillMaxSize(),
            navController = navController,
            startDestination = Screens.Welcome.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
            composable(Screens.Welcome.route) {
                currentScreen = Screens.Welcome
                WelcomeScreen(navController = navController)
            }
            composable(Screens.Login.route) {
                currentScreen = Screens.Login
                LoginScreen(navController = navController)
            }
            composable(Screens.Register.route) {
                currentScreen = Screens.Register
                RegisterScreen(navController = navController)
            }
        }
    }
}