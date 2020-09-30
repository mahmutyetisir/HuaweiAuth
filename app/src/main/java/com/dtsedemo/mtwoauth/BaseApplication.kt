package com.dtsedemo.mtwoauth

import android.app.Application
import com.dtsedemo.mtwoauth.di.managerModule
import com.dtsedemo.mtwoauth.di.myModule
import com.dtsedemo.mtwoauth.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(myModule, managerModule, viewModelModule)
        }
    }
}