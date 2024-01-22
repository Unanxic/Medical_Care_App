package com.example.medicalcareapp.di

import com.example.medicalcareapp.event_manager.EventManager
import com.example.medicalcareapp.viewmodels.HomepageViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val presentationModule = module {
    single { Dispatchers.IO }
    singleOf(::EventManager)
    viewModelOf(::HomepageViewModel)
}