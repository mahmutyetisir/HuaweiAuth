package com.dtsedemo.mtwoauth.viewmodel

import androidx.lifecycle.ViewModel
import com.dtsedemo.mtwoauth.model.ResultEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseViewModel : ViewModel() {
    lateinit var disposeBag: CompositeDisposable
    lateinit var loadingErrorSubs: Consumer<ResultEvent<*>>

}