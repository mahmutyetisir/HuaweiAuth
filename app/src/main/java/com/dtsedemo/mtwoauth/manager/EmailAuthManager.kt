package com.dtsedemo.mtwoauth.manager

import com.dtsedemo.mtwoauth.common.observableCreate
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.model.User
import com.huawei.agconnect.auth.*
import io.reactivex.rxjava3.core.Observable

interface EmailAuthManager : Auth {
    fun login(email: String, password: String): Observable<ResultEvent<Boolean>>
    fun register(email: String): Observable<ResultEvent<Boolean>>
    fun verifyCode(
        email: String,
        password: String,
        verifyCode: String
    ): Observable<ResultEvent<Boolean>>

    fun logout(): Observable<ResultEvent<Unit>>
    fun getUser(): User?
}

class HuaweiEmailAuthManagerImpl(
    agConnectAuth: AGConnectAuth,
    private val verifyCodeSettings: VerifyCodeSettings
) : BaseAuthManager<EmailAuthManager>(agConnectAuth), EmailAuthManager {
    override val job: EmailAuthManager = this

    override fun login(email: String, password: String): Observable<ResultEvent<Boolean>> {

        return observableCreate { subscriber ->

            val credential = EmailAuthProvider.credentialWithPassword(
                email,
                password
            )

            AGConnectAuth.getInstance().signIn(credential)
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

    override fun register(email: String): Observable<ResultEvent<Boolean>> {
        return observableCreate { subscriber ->

            EmailAuthProvider.requestVerifyCode(
                email,
                verifyCodeSettings
            ).addOnSuccessListener {
                subscriber.onNext(ResultEvent.Success(true))
                subscriber.onComplete()
            }.addOnFailureListener {
                subscriber.onNext(ResultEvent.Error(it))
                //subscriber.onNext(ResultEvent.Success(true))
                subscriber.onComplete()
            }
        }
    }

    override fun verifyCode(
        email: String,
        password: String,
        verifyCode: String
    ): Observable<ResultEvent<Boolean>> {
        return observableCreate { subscriber ->

            val user = EmailUser.Builder()
                .setEmail(email)
                .setPassword(password)
                .setVerifyCode(verifyCode)
                .build()

            AGConnectAuth.getInstance().createUser(user).addOnSuccessListener {
                subscriber.onNext(ResultEvent.Success(true))
                subscriber.onComplete()
            }.addOnFailureListener {
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