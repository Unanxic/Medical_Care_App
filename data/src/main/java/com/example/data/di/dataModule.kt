package com.example.data.di

import com.example.data.extensions.ContextExtensions
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {

    //Helper functions
    singleOf(::ContextExtensions)
}