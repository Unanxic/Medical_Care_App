package com.example.medicalcareapp.screens

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

            CurrentHomeScreen.ALLERGIES ->
                homeScreenManager.emitTopBarConfigs(TopBarConfigs(topBarLayout = TopBarLayouts.ALLERGIES_HISTORY_TEXT))

            CurrentHomeScreen.CONTACTS ->
                homeScreenManager.emitTopBarConfigs(TopBarConfigs(topBarLayout = TopBarLayouts.CONTACTS_TEXT))

            CurrentHomeScreen.ACCOUNT ->
                homeScreenManager.emitTopBarConfigs(TopBarConfigs(topBarLayout = TopBarLayouts.ACCOUNT_TEXT))
        }
    }

    TopBarLayout(
        navController = navController,
        topBarLayouts = topBarConfigs.topBarLayout,
        leftActionOnClick = topBarConfigs.leftActionClick
    ) { paddingValues ->
        when (currentScreen) {
            CurrentHomeScreen.NONE -> TODO()
            CurrentHomeScreen.HISTORY ->
                MedicineHistoryScreen(paddingValues = paddingValues)

            CurrentHomeScreen.ALLERGIES ->
                AllergiesScreen(paddingValues = paddingValues)

            CurrentHomeScreen.CONTACTS ->
                EmergencyContactsScreen(paddingValues = paddingValues)

            CurrentHomeScreen.ACCOUNT ->
                AccountScreen(paddingValues = paddingValues, navController = navController)
        }
    }

    topBarConfigs.leftActionClick?.let { backAction ->
        BackHandler(onBack = backAction)
    }
}