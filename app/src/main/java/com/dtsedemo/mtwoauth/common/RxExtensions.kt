package com.dtsedemo.mtwoauth.common

import android.content.Context
import android.view.View
import android.widget.Toast
import com.dtsedemo.mtwoauth.manager.Auth
import com.dtsedemo.mtwoauth.manager.BaseAuthManager
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.viewmodel.BaseViewModel
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun Disposable.addTo(compositeDisposable: CompositeDisposable): Disposable =
    apply { compositeDisposable.add(this) }

fun <T : ResultEvent<*>> Observable<T>.subscribeMultiple(
    baseViewModel: BaseViewModel,
    taskConsumer: Consumer<T>
) {
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    subscribe(baseViewModel.loadingErrorSubs).addTo(baseViewModel.disposeBag)
    subscribe(taskConsumer).addTo(baseViewModel.disposeBag)
}

fun <S : Any> Any.observableCreate(callback: (subscriber: ObservableEmitter<ResultEvent<S>>) -> Unit): Observable<ResultEvent<S>> {
    return Observable.create {
        it.onNext(ResultEvent.InProgress)
        callback.invoke(it)
    }
}

fun View.click(compositeDisposable: CompositeDisposable, listener: () -> Unit) {
    this.clicks()
        .throttleFirst(400, TimeUnit.MILLISECONDS)
        .retry()
        .subscribe {
            listener()
        }
        .addTo(compositeDisposable)
}

fun Context.toast(message: String? = null) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}