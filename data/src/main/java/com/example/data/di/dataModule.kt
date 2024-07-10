package com.example.data.di

import com.example.data.database.sources.RealmDataSource
import com.example.data.database.sources.RealmDataSourceImpl//import com.example.data.database.sources.RealmDataSourceImpl
import com.example.data.extensions.ContextExtensions
import com.example.data.repositories.locale.LocaleRepositoryImpl
import com.example.data.repositories.sos_contact.SOSContactRepositoryImpl
import com.example.domain.repositories.locale.LocaleRepository
import com.example.domain.repositories.sos_contact.SOSContactRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    //Localization
    singleOf(::LocaleRepositoryImpl) bind LocaleRepository::class
    //Helper functions
    singleOf(::ContextExtensions)
    //Realm
    singleOf(::RealmDataSourceImpl) bind RealmDataSource::class
    //SOS
    singleOf(::SOSContactRepositoryImpl) bind SOSContactRepository::class
}