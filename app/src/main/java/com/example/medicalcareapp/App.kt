package com.example.medicalcareapp

import android.app.Application
import com.example.data.database.sources.RealmDataSource
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import com.example.medicalcareapp.di.presentationModule
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
        setUpRealmDb()
    }

    private fun startKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(presentationModule, dataModule, domainModule)
        }
    }

    private fun setUpRealmDb() {
        val realmDataSource: RealmDataSource = getKoin().get()
        realmDataSource.initializeRealm(this)
    }
}