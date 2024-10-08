package com.example.medicalcareapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.domain.usecases.account.UserAccountUseCase
import com.example.medicalcareapp.event_manager.AppEvents
import com.example.medicalcareapp.event_manager.EventManager
import com.example.medicalcareapp.navigation.graphs.addContactsNavigation
import com.example.medicalcareapp.navigation.graphs.registerMedicineNavigation
import com.example.medicalcareapp.screens.account_settings.screens.account_details.AccountDetailsScreen
import com.example.medicalcareapp.screens.account_settings.screens.account_details.delete.DetailsSuccessDeleteScreen
import com.example.medicalcareapp.screens.account_settings.screens.account_details.success.DetailsSuccessScreen
import com.example.medicalcareapp.screens.account_settings.screens.change_language.ChangeLanguageScreen
import com.example.medicalcareapp.screens.account_settings.screens.sos_contact.SOSContactScreen
import com.example.medicalcareapp.screens.account_settings.screens.sos_contact.SuccessfulContactSubmitScreen
import com.example.medicalcareapp.screens.account_settings.screens.sos_contact.SuccessfulContactSuccessDeleteScreen
import com.example.medicalcareapp.screens.contact_details_screen.ContactDetailsScreen
import com.example.medicalcareapp.screens.home_screen.HomeScreen
import com.example.medicalcareapp.screens.login_screen.LoginScreen
import com.example.medicalcareapp.screens.medicine_details_screen.MedicineDetailsScreen
import com.example.medicalcareapp.screens.no_internet_screen.NoInternetScreen
import com.example.medicalcareapp.screens.register_screen.RegisterScreen
import com.example.medicalcareapp.screens.register_screen.SuccessfulRegisterScreen
import com.example.medicalcareapp.screens.reminder_details_screen.ReminderScreen
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
    val accountUseCase: UserAccountUseCase = koinInject()
    val accountState by accountUseCase.authState.collectAsState()

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
                SplashScreen(navController = navController, accountState = accountState)
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
                currentScreen = Screens.SOSContactSuccessDelete
                SuccessfulContactSuccessDeleteScreen(navController = navController)
            }
            composable(Screens.DetailsSuccess.route) {
                currentScreen = Screens.DetailsSuccess
                DetailsSuccessScreen(navController = navController)
            }
            composable(Screens.DetailsSuccessDelete.route) {
                currentScreen = Screens.DetailsSuccessDelete
                DetailsSuccessDeleteScreen(navController = navController)
            }
            composable(Screens.AccountDetails.route) {
                currentScreen = Screens.AccountDetails
                AccountDetailsScreen(navController = navController)
            }
            composable(
                route = "Screens.AddReminder.route/{medicationName}",
                arguments = listOf(navArgument("medicationName") { type = NavType.StringType })
            ) { backStackEntry ->
                val medicationName = backStackEntry.arguments?.getString("medicationName")
                AddReminderScreen(navController = navController, medicationName = medicationName ?: "")
            }
            composable(Screens.SuccessfulAddReminder.route) {
                currentScreen = Screens.SuccessfulAddReminder
                SuccessfulAddReminderScreen(navController = navController)
            }
            composable(Screens.SuccessfulRegistration.route) {
                currentScreen = Screens.SuccessfulRegistration
                SuccessfulRegisterScreen(navController = navController)
            }
            composable(
                route = "${Screens.MedicineDetails.route}/{medicationId}",
                arguments = listOf(navArgument("medicationId") { type = NavType.StringType })
            ) {
                val medicationId = it.arguments?.getString("medicationId") ?: return@composable
                MedicineDetailsScreen(navController = navController, medicationId = medicationId)
            }
            composable(
                route = "${Screens.ContactDetails.route}/{contactId}",
                arguments = listOf(navArgument("contactId") { type = NavType.StringType })
            ) {
                val contactId = it.arguments?.getString("contactId") ?: return@composable
                ContactDetailsScreen(navController = navController, contactId = contactId)
            }
            composable(
                route = "${Screens.ReminderDetails.route}/{reminderId}/{medicationName}/{selectedTime}",
                arguments = listOf(
                    navArgument("reminderId") { type = NavType.StringType },
                    navArgument("medicationName") { type = NavType.StringType },
                    navArgument("selectedTime") { type = NavType.StringType } // New argument for selectedTime
                )
            ) { backStackEntry ->
                val reminderId = backStackEntry.arguments?.getString("reminderId") ?: return@composable
                val medicationName = backStackEntry.arguments?.getString("medicationName") ?: return@composable
                val selectedTime = backStackEntry.arguments?.getString("selectedTime") ?: return@composable

                ReminderScreen(
                    navController = navController,
                    reminderId = reminderId,
                    medicationName = medicationName,
                    selectedTime = selectedTime // Pass the selectedTime to the ReminderScreen
                )
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