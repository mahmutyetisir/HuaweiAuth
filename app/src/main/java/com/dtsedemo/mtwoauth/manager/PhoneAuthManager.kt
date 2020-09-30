package com.dtsedemo.mtwoauth.manager

import com.dtsedemo.mtwoauth.common.observableCreate
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.model.User
import com.huawei.agconnect.auth.*
import io.reactivex.rxjava3.core.Observable

interface PhoneAuthManager : Auth {
    fun login(phoneNumber: String, password: String): Observable<ResultEvent<Boolean>>
    fun register(phoneNumber: String): Observable<ResultEvent<Boolean>>
    fun verifyCode(
        phoneNumber: String,
        password: String,
        verifyCode: String
    ): Observable<ResultEvent<Boolean>>

    fun logout(): Observable<ResultEvent<Unit>>
    fun getUser(): User?
}

class HuaweiPhoneAuthManagerImpl(
    agConnectAuth: AGConnectAuth,
    val verifyCodeSettings: VerifyCodeSettings
) : BaseAuthManager<PhoneAuthManager>(agConnectAuth), PhoneAuthManager {
    override val job: PhoneAuthManager = this

    override fun login(phoneNumber: String, password: String): Observable<ResultEvent<Boolean>> {
        return observableCreate { subscriber ->
            val credential = PhoneAuthProvider.credentialWithPassword(
                "90",
                phoneNumber,
                password
            )

            agConnectAuth.signIn(credential)
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

    override fun register(phoneNumber: String): Observable<ResultEvent<Boolean>> {
        return observableCreate { subscriber ->

            PhoneAuthProvider.requestVerifyCode(
                "90",
                phoneNumber,
                verifyCodeSettings
            ).addOnSuccessListener {
                subscriber.onNext(ResultEvent.Success(true))
                subscriber.onComplete()
            }.addOnFailureListener {
                subscriber.onNext(ResultEvent.Error(it))
                subscriber.onComplete()
            }
        }
    }

    override fun verifyCode(
        phoneNumber: String,
        password: String,
        verifyCode: String
    ): Observable<ResultEvent<Boolean>> {
        return observableCreate { subscriber ->

            val user = PhoneUser.Builder()
                .setCountryCode("90")
                .setPhoneNumber(phoneNumber)
                .setPassword(password)
                .setVerifyCode(verifyCode)
                .build()

            agConnectAuth.createUser(user).addOnSuccessListener {
                subscriber.onNext(ResultEvent.Success(true))
                subscriber.onComplete()
            }.addOnFailureListener {
                subscriber.onNext(ResultEvent.Error(it))
                subscriber.onComplete()
            }
        }
    }

    override fun getUser(): User? {
        val currentUser = agConnectAuth.currentUser
        return if (currentUser == null) null else User(
            currentUser.phone,
            currentUser.uid,
            currentUser.displayName
        )
    }

}