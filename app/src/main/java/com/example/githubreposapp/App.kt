package com.example.githubreposapp

import android.app.Application
import com.example.githubreposapp.di.networkModule
import com.example.githubreposapp.di.repositoryModule
import com.example.githubreposapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(networkModule, viewModelModule, repositoryModule)
        }
    }
}