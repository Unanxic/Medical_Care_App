package com.example.medicalcareapp.di

import android.app.Activity
import com.example.medicalcareapp.MedicationNotificationService
import com.example.medicalcareapp.event_manager.EventManager
import com.example.medicalcareapp.managers.HomeScreenManager
import com.example.medicalcareapp.managers.LoaderManager
import com.example.medicalcareapp.screens.account_screen.viewmodel.AccountViewModel
import com.example.medicalcareapp.screens.account_settings.screens.account_details.viewmodel.AccountDetailsViewModel
import com.example.medicalcareapp.screens.account_settings.viewmodels.SOSContactViewModel
import com.example.medicalcareapp.screens.add_contacts_screen.viewmodel.ContactViewModel
import com.example.medicalcareapp.screens.login_screen.viewmodel.LoginViewModel
import com.example.medicalcareapp.screens.medicine_history_screen.viewmodels.MedicationViewModel
import com.example.medicalcareapp.screens.register_screen.viewmodel.RegisterViewModel
import com.example.medicalcareapp.screens.reminder_screen.viewmodel.ReminderViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.lang.ref.WeakReference

val presentationModule = module {
    single { Dispatchers.IO }
    singleOf(::EventManager)
    singleOf(::LoaderManager)
    singleOf(::HomeScreenManager)
    viewModelOf(::SOSContactViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::AccountViewModel)
    viewModelOf(::MedicationViewModel)
    viewModelOf(::ContactViewModel)
    viewModelOf(::AccountDetailsViewModel)
    viewModelOf(::ReminderViewModel)
    singleOf(::MedicationNotificationService)
}

fun getActivityKoinModule(activity: Activity) = module {
    single { WeakReference(activity) }
}