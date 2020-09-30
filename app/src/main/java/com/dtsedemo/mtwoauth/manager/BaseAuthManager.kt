package com.dtsedemo.mtwoauth.manager

import com.dtsedemo.mtwoauth.common.observableCreate
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.huawei.agconnect.auth.AGConnectAuth
import io.reactivex.rxjava3.core.Observable

interface Auth

interface BaseAuth<T : Auth> {
    val job: T

    fun logout(): Observable<ResultEvent<Unit>>
}

abstract class BaseAuthManager<T : Auth>(val agConnectAuth: AGConnectAuth) : BaseAuth<T> {

    override fun logout(): Observable<ResultEvent<Unit>> {
        return observableCreate {
            agConnectAuth.signOut()
            it.onNext(ResultEvent.Success(Unit))
            it.onComplete()
        }
    }
}