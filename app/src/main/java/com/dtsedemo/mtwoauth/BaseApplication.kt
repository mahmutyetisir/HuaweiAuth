package com.dtsedemo.mtwoauth

import android.app.Application
import com.dtsedemo.mtwoauth.di.managerModule
import com.dtsedemo.mtwoauth.di.myModule
import com.dtsedemo.mtwoauth.di.viewModelModule
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val authConfig = TwitterAuthConfig(
            "APP_KEY",
            "APP_SECRET"
        )
        val twitterConfig =
            TwitterConfig.Builder(this).twitterAuthConfig(authConfig).debug(true).build()
        Twitter.initialize(twitterConfig)
        
        startKoin {
            androidContext(this@BaseApplication)
            modules(myModule, managerModule, viewModelModule)
        }
    }
}