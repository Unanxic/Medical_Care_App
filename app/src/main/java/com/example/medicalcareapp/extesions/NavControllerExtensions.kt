package com.example.medicalcareapp.extesions

import androidx.navigation.NavController
import com.example.medicalcareapp.composables.top_bar.TopBarConfigs
import com.example.medicalcareapp.composables.top_bar.TopBarLayouts
import com.example.medicalcareapp.managers.CurrentHomeScreen
import com.example.medicalcareapp.managers.HomeScreenManager
import com.example.medicalcareapp.navigation.Screens
import org.koin.java.KoinJavaComponent

fun NavController.navigateToHomeScreen(
    currentHomeScreen: CurrentHomeScreen? = null,
    topBarConfig: TopBarConfigs = TopBarConfigs("", TopBarLayouts.MEDICAL_HISTORY_TEXT)
) {
    val homeScreenManager = KoinJavaComponent.get<HomeScreenManager>(HomeScreenManager::class.java)

    val navigateToHome = {
        kotlin.runCatching {
            this.popBackStack(Screens.Home.route, false)
        }.onFailure {
            this.navigate(Screens.Home.route)
        }
    }

    homeScreenManager.emitTopBarConfigs(topBarConfig)

    currentHomeScreen?.let { currentScreen ->
        homeScreenManager.emitCurrentScreen(currentScreen) {
            navigateToHome()
        }
    } ?: navigateToHome()
}