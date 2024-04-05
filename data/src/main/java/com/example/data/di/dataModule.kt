package com.example.data.di

import com.example.data.extensions.ContextExtensions
import com.example.data.repositories.LocaleRepositoryImpl
import com.example.domain.repositories.locale.LocaleRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    //Localization
    singleOf(::LocaleRepositoryImpl) bind LocaleRepository::class

    //Helper functions
    singleOf(::ContextExtensions)
}