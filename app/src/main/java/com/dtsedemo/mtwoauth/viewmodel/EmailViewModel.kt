package com.dtsedemo.mtwoauth.viewmodel

import com.dtsedemo.mtwoauth.common.subscribeMultiple
import com.dtsedemo.mtwoauth.manager.HuaweiEmailAuthManagerImpl
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.model.User
import io.reactivex.rxjava3.functions.Consumer

class EmailViewModel(private val manager: HuaweiEmailAuthManagerImpl) : BaseViewModel() {
    var loginSubscription = Consumer<ResultEvent<Boolean>> {}
    var registerSubscription = Consumer<ResultEvent<Boolean>> {}
    var verifySubscription = Consumer<ResultEvent<Boolean>> {}
    var logoutSubscription = Consumer<ResultEvent<Unit>> {}

    fun login(email: String, password: String) {
        manager.job.login(email, password).subscribeMultiple(this, loginSubscription)
    }

    fun register(email: String) {
        manager.job.register(email).subscribeMultiple(this, registerSubscription)
    }

    fun verifyCode(
        email: String,
        password: String,
        verifyCode: String
    ) {
        manager.job.verifyCode(email, password, verifyCode)
            .subscribeMultiple(this, verifySubscription)
    }

    fun logout() {
        manager.job.logout().subscribeMultiple(this, logoutSubscription)
    }

    fun getUser(): User? {
        return manager.job.getUser()
    }
}