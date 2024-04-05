package com.example.medicalcareapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.example.medicalcareapp.navigation.MainNavController
import com.example.medicalcareapp.ui.theme.MedicalAppAndroidTheme
import com.example.medicalcareapp.utilities.LanguageHelper
import com.example.medicalcareapp.utilities.LanguageHelperEvents

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideLocalContext(content = {
                MedicalAppAndroidTheme {
                    Surface(
                        // .imePadding() moved to specific screens in order to keep the background images stationary see LoginScreen
                        modifier = Modifier
                            .fillMaxSize()
                            .systemBarsPadding()
                    ) {
                        MainNavController()
                    }
                }
            })
        }
    }

    @Composable
    private fun ProvideLocalContext(content: @Composable () -> Unit) {
        val context = LocalContext.current

        var updatedContext by remember { mutableStateOf(context) }
        LaunchedEffect(Unit) {
            LanguageHelper.setSelectedLanguage(context, LanguageHelper.getLanguage())
            LanguageHelper.updateEvents.collect {
                when (it) {
                    is LanguageHelperEvents.LanguageChanged -> {
                        updatedContext = it.context
                    }
                }
            }
        }

        CompositionLocalProvider(
            LocalContext provides updatedContext
        ) {
            content()
        }
    }
}