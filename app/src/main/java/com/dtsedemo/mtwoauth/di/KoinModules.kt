package com.dtsedemo.mtwoauth.di

import com.dtsedemo.mtwoauth.manager.FacebookAuthManagerImpl
import com.dtsedemo.mtwoauth.manager.HuaweiEmailAuthManagerImpl
import com.dtsedemo.mtwoauth.manager.HuaweiPhoneAuthManagerImpl
import com.dtsedemo.mtwoauth.manager.TwitterAuthManagerImpl
import com.dtsedemo.mtwoauth.viewmodel.*
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.auth.VerifyCodeSettings
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.*

object VerifyCode {
    fun getSettings(): VerifyCodeSettings = VerifyCodeSettings.newBuilder()
        .action(VerifyCodeSettings.ACTION_REGISTER_LOGIN) //ACTION_REGISTER_LOGIN/ACTION_RESET_PASSWORD
        .sendInterval(30) // Minimum sending interval, which ranges from 30s to 120s.
        .locale(Locale.ENGLISH) // Optional. It indicates the language for sending a verification code. The value of locale must contain the language and country/region information. The defualt value is Locale.getDefault.
        .build()
}

val myModule = module {
    single { VerifyCode.getSettings() }
    single { AGConnectAuth.getInstance() }
}

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { PhoneViewModel(get()) }
    viewModel { EmailViewModel(get()) }
    viewModel { FacebookViewModel(get()) }
    viewModel { TwitterViewModel(get()) }
}

val managerModule = module {
    single { HuaweiPhoneAuthManagerImpl(get(), get()) }
    single { HuaweiEmailAuthManagerImpl(get(), get()) }
    single { FacebookAuthManagerImpl(get()) }
    single { TwitterAuthManagerImpl(get()) }
}