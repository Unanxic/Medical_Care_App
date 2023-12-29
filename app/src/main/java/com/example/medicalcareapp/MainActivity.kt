package com.example.medicalcareapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.medicalcareapp.navigation.MainNavController
import com.example.medicalcareapp.ui.theme.MedicalAppAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
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
        }
    }
}