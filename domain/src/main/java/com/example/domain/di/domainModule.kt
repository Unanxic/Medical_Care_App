package com.example.domain.di

import com.example.domain.usecases.locale.LocaleUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::LocaleUseCase)
}