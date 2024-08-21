package com.example.medicalcareapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.WindowCompat
import com.example.medicalcareapp.di.getActivityKoinModule
import com.example.medicalcareapp.navigation.MainNavController
import com.example.medicalcareapp.ui.theme.MedicalAppAndroidTheme
import com.example.medicalcareapp.utilities.LanguageHelper
import com.example.medicalcareapp.utilities.LanguageHelperEvents
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.unloadKoinModules

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(getActivityKoinModule(this))
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (!isNotificationPermissionGranted(applicationContext)) {
            requestNotificationPermission(this)
        }

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
        parseIntent(intent)
    }
    override fun onDestroy() {
        super.onDestroy()
        // Unload Activity specific Koin module
        unloadKoinModules(getActivityKoinModule(this))
    }

    private fun isNotificationPermissionGranted(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return NotificationManagerCompat.from(context).areNotificationsEnabled()
        }
        return true
    }

    private fun requestNotificationPermission(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Prompt user to open settings manually
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Notification Permission")
                .setMessage("Notification permission is required. Please enable it in settings.")
                .setPositiveButton("Open Settings") { dialog, _ ->
                    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.packageName)
                    activity.startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    private fun parseIntent(intent: Intent?) {
        val isMedicationNotification = intent?.getBooleanExtra(MEDICATION_NOTIFICATION, false) ?: false
        if (isMedicationNotification) {
            //todo
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