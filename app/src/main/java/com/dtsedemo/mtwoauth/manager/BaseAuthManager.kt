package com.dtsedemo.mtwoauth.manager

import com.huawei.agconnect.auth.AGConnectAuth

interface Auth

interface BaseAuth<T : Auth> {
    val job: T
}

abstract class BaseAuthManager<T : Auth>(val agConnectAuth: AGConnectAuth) : BaseAuth<T>