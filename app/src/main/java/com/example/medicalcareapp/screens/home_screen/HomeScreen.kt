package com.example.medicalcareapp.screens.home_screen

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.medicalcareapp.composables.top_bar.TopBarConfigs
import com.example.medicalcareapp.composables.top_bar.TopBarLayout
import com.example.medicalcareapp.composables.top_bar.TopBarLayouts
import com.example.medicalcareapp.event_manager.EventManager
import com.example.medicalcareapp.managers.CurrentHomeScreen
import com.example.medicalcareapp.managers.HomeScreenManager
import com.example.medicalcareapp.screens.account_screen.AccountScreen
import com.example.medicalcareapp.screens.calendar_screen.CalendarScreen
import com.example.medicalcareapp.screens.emergency_contacts_screen.EmergencyContactsScreen
import com.example.medicalcareapp.screens.medicine_history_screen.screen.MedicineHistoryScreen
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeScreenManager: HomeScreenManager = koinInject(),
    eventManager: EventManager = koinInject(),
) {
    val currentScreen by homeScreenManager.currentHomeScreen.collectAsState(initial = CurrentHomeScreen.HISTORY)
    val topBarConfigs by homeScreenManager.topBarConfigs.collectAsState()

    LaunchedEffect(currentScreen) {
        when (currentScreen) {
            CurrentHomeScreen.NONE -> TODO()
            CurrentHomeScreen.HISTORY ->
                homeScreenManager.emitTopBarConfigs(TopBarConfigs(topBarLayout = TopBarLayouts.MEDICAL_HISTORY_TEXT))

            CurrentHomeScreen.CONTACTS ->
                homeScreenManager.emitTopBarConfigs(TopBarConfigs(topBarLayout = TopBarLayouts.CONTACTS_TEXT))

            CurrentHomeScreen.ACCOUNT ->
                homeScreenManager.emitTopBarConfigs(TopBarConfigs(topBarLayout = TopBarLayouts.ACCOUNT_TEXT))

            CurrentHomeScreen.CALENDAR ->
                homeScreenManager.emitTopBarConfigs(TopBarConfigs(topBarLayout = TopBarLayouts.CALENDAR_TEXT))
        }
    }

    TopBarLayout(
        navController = navController,
        topBarLayouts = topBarConfigs.topBarLayout,
        leftActionOnClick = topBarConfigs.leftActionClick,
    ) { paddingValues ->
        when (currentScreen) {
            CurrentHomeScreen.NONE -> TODO()
            CurrentHomeScreen.HISTORY ->
                MedicineHistoryScreen(paddingValues = paddingValues, navController = navController)

            CurrentHomeScreen.CONTACTS ->
                EmergencyContactsScreen(paddingValues = paddingValues, navController = navController)

            CurrentHomeScreen.ACCOUNT ->
                AccountScreen(paddingValues = paddingValues, navController = navController)

            CurrentHomeScreen.CALENDAR ->
                CalendarScreen(paddingValues = paddingValues, navController = navController)
        }
    }

    topBarConfigs.leftActionClick?.let { backAction ->
        BackHandler(onBack = backAction)
    }
}