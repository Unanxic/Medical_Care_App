package com.example.medicalcareapp.composables.top_bar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.medicalcareapp.composables.BottomBar
import com.example.medicalcareapp.managers.HomeScreenManager
import org.koin.compose.koinInject

enum class TopBarLayouts {
    MEDICAL_HISTORY_TEXT,
    ALLERGIES_HISTORY_TEXT,
    CONTACTS_TEXT,
    ACCOUNT_TEXT,
    BACK,
    NONE
}

@Composable
fun TopBarLayout(
    modifier: Modifier = Modifier,
    topBarTitle: String = "",
    navController: NavController,
    topBarLayouts: TopBarLayouts,
    leftActionOnClick: (() -> Unit)? = null,
    homeScreenManager: HomeScreenManager = koinInject(),
    content: @Composable (PaddingValues) -> Unit
) {

    var isNavigating by remember { mutableStateOf(false) }
    val navigateFunction by remember { mutableStateOf({}) }
    var isNavigationFunctionInitialized by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(navigateFunction) {
        if (!isNavigating && isNavigationFunctionInitialized) {
            isNavigating = true
            navigateFunction()
        }

        isNavigationFunctionInitialized = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = modifier,
            topBar = {
                if (topBarLayouts == TopBarLayouts.MEDICAL_HISTORY_TEXT) {
                    Column {
                        TopBar(
                            topBarLayout = TopBarLayouts.MEDICAL_HISTORY_TEXT,
                            navController = navController,
                        )
                    }
                }
                else if (topBarLayouts == TopBarLayouts.ALLERGIES_HISTORY_TEXT) {
                    Column {
                        TopBar(
                            topBarLayout = TopBarLayouts.ALLERGIES_HISTORY_TEXT,
                            navController = navController,
                        )
                    }
                }
                else if (topBarLayouts == TopBarLayouts.CONTACTS_TEXT) {
                    Column {
                        TopBar(
                            topBarLayout = TopBarLayouts.CONTACTS_TEXT,
                            navController = navController,
                        )
                    }
                }
                else if (topBarLayouts == TopBarLayouts.ACCOUNT_TEXT) {
                    Column {
                        TopBar(
                            topBarLayout = TopBarLayouts.ACCOUNT_TEXT,
                            navController = navController,
                        )
                    }
                }
                else if (topBarLayouts != TopBarLayouts.BACK) {
                    TopBar(
                        topBarLayouts,
                        navController = navController,
                        header = topBarTitle,
                        leftActionOnClick = leftActionOnClick
                    )
                }
            },
            bottomBar = {
                BottomBar(homeScreenManager)
            }
        ) { paddingValues ->
            content(paddingValues)
        }
    }
}