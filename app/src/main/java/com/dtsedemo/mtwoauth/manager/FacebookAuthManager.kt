package com.dtsedemo.mtwoauth.manager

import com.dtsedemo.mtwoauth.common.observableCreate
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.model.User
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.auth.FacebookAuthProvider
import io.reactivex.rxjava3.core.Observable

interface FacebookAuthManager : Auth {
    fun login(accessToken: String): Observable<ResultEvent<Boolean>>
    fun logout(): Observable<ResultEvent<Unit>>
    fun getUser(): User?
}

class FacebookAuthManagerImpl(agConnectAuth: AGConnectAuth) :
    BaseAuthManager<FacebookAuthManager>(agConnectAuth), FacebookAuthManager {
    override val job: FacebookAuthManager = this

    override fun login(accessToken: String): Observable<ResultEvent<Boolean>> {
        return observableCreate { subscriber ->
            AGConnectAuth.getInstance()
                .signIn(FacebookAuthProvider.credentialWithToken(accessToken))
                .addOnSuccessListener {
                    subscriber.onNext(ResultEvent.Success(true))
                    subscriber.onComplete()
                }
                .addOnFailureListener {
                    subscriber.onNext(ResultEvent.Error(it))
                    subscriber.onComplete()
                }
        }
    }

    override fun logout(): Observable<ResultEvent<Unit>> {
        return observableCreate {
            agConnectAuth.signOut()
            it.onNext(ResultEvent.Success(Unit))
            it.onComplete()
        }
    }

    override fun getUser(): User? {
        val currentUser = agConnectAuth.currentUser
        return if (currentUser == null) null else User(
            currentUser.email,
            currentUser.uid,
            currentUser.displayName
        )
    }

}