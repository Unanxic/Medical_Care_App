package com.example.medicalcareapp.di

import com.example.medicalcareapp.event_manager.EventManager
import com.example.medicalcareapp.managers.HomeScreenManager
import com.example.medicalcareapp.screens.account_settings.viewmodels.SOSContactViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val presentationModule = module {
    single { Dispatchers.IO }
    singleOf(::EventManager)
    singleOf(::HomeScreenManager)
    viewModelOf(::SOSContactViewModel)
}