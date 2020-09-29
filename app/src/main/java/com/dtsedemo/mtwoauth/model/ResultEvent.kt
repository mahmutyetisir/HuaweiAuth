package com.dtsedemo.mtwoauth.model

sealed class ResultEvent<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultEvent<T>()
    data class Error(val exception: Exception) : ResultEvent<Nothing>()
    object InProgress : ResultEvent<Nothing>()
}