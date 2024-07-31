package com.example.medicalcareapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medicalcareapp.event_manager.AppEvents
import com.example.medicalcareapp.event_manager.EventManager
import com.example.medicalcareapp.navigation.graphs.addContactsNavigation
import com.example.medicalcareapp.navigation.graphs.registerMedicineNavigation
import com.example.medicalcareapp.screens.account_settings.screens.account_details.AccountDetailsScreen
import com.example.medicalcareapp.screens.account_settings.screens.change_language.ChangeLanguageScreen
import com.example.medicalcareapp.screens.account_settings.screens.sos_contact.SOSContactScreen
import com.example.medicalcareapp.screens.account_settings.screens.sos_contact.SuccessfulContactSubmitScreen
import com.example.medicalcareapp.screens.account_settings.screens.sos_contact.SuccessfulContactSuccessDeleteScreen
import com.example.medicalcareapp.screens.home_screen.HomeScreen
import com.example.medicalcareapp.screens.login_screen.LoginScreen
import com.example.medicalcareapp.screens.no_internet_screen.NoInternetScreen
import com.example.medicalcareapp.screens.register_screen.RegisterScreen
import com.example.medicalcareapp.screens.reminder_screen.AddReminderScreen
import com.example.medicalcareapp.screens.reminder_screen.SuccessfulAddReminderScreen
import com.example.medicalcareapp.screens.splash_screen.SplashScreen
import com.example.medicalcareapp.screens.welcome_screen.WelcomeScreen
import org.koin.compose.koinInject

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavController(
    eventManager: EventManager = koinInject()
) {
    val navController = rememberNavController()
    var currentScreen by remember { mutableStateOf<Screens>(Screens.Welcome) }
    var showNoInternetScreen by remember { mutableStateOf(false) }

    LaunchedEffect(eventManager.currentAppEvent) {
        eventManager.currentAppEvent.collect { event ->
            when (event) {
                is AppEvents.ShowNoInternetScreen ->
                    showNoInternetScreen = true

                is AppEvents.HideNoInternetScreen ->
                    showNoInternetScreen = false

                else -> {
                }
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        NavHost(
            modifier = Modifier
                .fillMaxSize(),
            navController = navController,
            startDestination = Screens.Splash.route,
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
            composable(Screens.Splash.route) {
                currentScreen = Screens.Splash
                SplashScreen(navController = navController)
            }
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
            composable(Screens.Home.route) {
                currentScreen = Screens.Home
                HomeScreen(navController = navController)
            }
            composable(Screens.ChangeLanguage.route) {
                currentScreen = Screens.ChangeLanguage
                ChangeLanguageScreen(navController = navController)
            }
            composable(Screens.SOSContact.route) {
                currentScreen = Screens.SOSContact
                SOSContactScreen(navController = navController)
            }
            composable(Screens.SOSContactSuccess.route) {
                currentScreen = Screens.SOSContactSuccess
                SuccessfulContactSubmitScreen(navController = navController)
            }
            composable(Screens.SOSContactSuccessDelete.route) {
                currentScreen = Screens.SOSContactSuccess
                SuccessfulContactSuccessDeleteScreen(navController = navController)
            }
            composable(Screens.AccountDetails.route) {
                currentScreen = Screens.AccountDetails
                AccountDetailsScreen(navController = navController)
            }
            composable(Screens.AddReminder.route) {
                currentScreen = Screens.AddReminder
                AddReminderScreen(navController = navController)
            }
            composable(Screens.SuccessfulAddReminder.route) {
                currentScreen = Screens.SuccessfulAddReminder
                SuccessfulAddReminderScreen(navController = navController)
            }
            registerMedicineNavigation(navController) {
                currentScreen = it
            }
            addContactsNavigation(navController) {
                currentScreen = it
            }
        }
        if (showNoInternetScreen) {
            NoInternetScreen()
        }
    }
}