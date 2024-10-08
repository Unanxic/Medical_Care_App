package com.example.medicalcareapp.composables.top_bar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medicalcareapp.composables.BottomBar
import com.example.medicalcareapp.composables.SOSButton
import com.example.medicalcareapp.managers.HomeScreenManager
import com.example.medicalcareapp.screens.account_settings.viewmodels.SOSContactViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

enum class TopBarLayouts {
    CALENDAR_TEXT,
    MEDICAL_HISTORY_TEXT,
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
    sosContactViewModel: SOSContactViewModel = koinViewModel(),
    homeScreenManager: HomeScreenManager = koinInject(),
    content: @Composable (PaddingValues) -> Unit,
) {

    val sosContact by sosContactViewModel.sosContact.collectAsState()
    val context = LocalContext.current

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
                } else if (topBarLayouts == TopBarLayouts.CONTACTS_TEXT) {
                    Column {
                        TopBar(
                            topBarLayout = TopBarLayouts.CONTACTS_TEXT,
                            navController = navController,
                        )
                    }
                } else if (topBarLayouts == TopBarLayouts.CALENDAR_TEXT) {
                    Column {
                        TopBar(
                            topBarLayout = TopBarLayouts.CALENDAR_TEXT,
                            navController = navController,
                        )
                    }
                } else if (topBarLayouts == TopBarLayouts.ACCOUNT_TEXT) {
                    Column {
                        TopBar(
                            topBarLayout = TopBarLayouts.ACCOUNT_TEXT,
                            navController = navController,
                        )
                    }
                } else if (topBarLayouts != TopBarLayouts.BACK) {
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
            Box(modifier = Modifier.fillMaxSize()) {
                content(paddingValues)
                if (sosContact != null) {
                    SOSButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 9.5.dp, bottom = 66.dp)
                    ) {
                        sosContactViewModel.makePhoneCall(context)
                    }
                }
            }
        }
    }
}