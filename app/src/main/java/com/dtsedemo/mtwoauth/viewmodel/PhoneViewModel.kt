package com.dtsedemo.mtwoauth.viewmodel

import com.dtsedemo.mtwoauth.common.subscribeMultiple
import com.dtsedemo.mtwoauth.manager.HuaweiPhoneAuthManagerImpl
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.model.User
import io.reactivex.rxjava3.functions.Consumer

class PhoneViewModel(private val manager: HuaweiPhoneAuthManagerImpl) : BaseViewModel() {
    var loginSubscription = Consumer<ResultEvent<Boolean>> {}
    var registerSubscription = Consumer<ResultEvent<Boolean>> {}
    var verifySubscription = Consumer<ResultEvent<Boolean>> {}
    var logoutSubscription = Consumer<ResultEvent<Unit>> {}

    fun login(phoneNumber: String, password: String) {
        manager.login(phoneNumber, password).subscribeMultiple(this, loginSubscription)
    }

    fun register(phoneNumber: String) {
        manager.register(phoneNumber).subscribeMultiple(this, registerSubscription)
    }

    fun verifyCode(
        phoneNumber: String,
        password: String,
        verifyCode: String
    ) {
        manager.verifyCode(phoneNumber, password, verifyCode)
            .subscribeMultiple(this, verifySubscription)
    }

    fun logout() {
        manager.logout().subscribeMultiple(this, logoutSubscription)
    }

    fun getUser(): User? {
        return manager.getUser()
    }
}