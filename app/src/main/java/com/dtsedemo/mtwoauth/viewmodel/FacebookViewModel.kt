package com.dtsedemo.mtwoauth.viewmodel

import com.dtsedemo.mtwoauth.common.subscribeMultiple
import com.dtsedemo.mtwoauth.manager.FacebookAuthManagerImpl
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.model.User
import io.reactivex.rxjava3.functions.Consumer

class FacebookViewModel(private val manager: FacebookAuthManagerImpl) : BaseViewModel() {
    var loginSubscription = Consumer<ResultEvent<Boolean>> {}
    var logoutSubscription = Consumer<ResultEvent<Unit>> {}

    fun login(accessToken: String) {
        manager.job.login(accessToken).subscribeMultiple(this, loginSubscription)
    }

    fun logout() {
        manager.job.logout().subscribeMultiple(this, logoutSubscription)
    }

    fun getUser(): User? {
        return manager.job.getUser()
    }
}