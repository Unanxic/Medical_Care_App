package com.example.medicalcareapp.di

import com.example.medicalcareapp.event_manager.EventManager
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val presentationModule = module {
    single { Dispatchers.IO }
    singleOf(::EventManager)
}