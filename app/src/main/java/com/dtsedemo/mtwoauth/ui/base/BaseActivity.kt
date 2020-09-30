package com.dtsedemo.mtwoauth.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dtsedemo.mtwoauth.common.toast
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.viewmodel.BaseViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer


abstract class BaseActivity : AppCompatActivity() {
    abstract val pageTitle: String
    abstract val res: Int
    abstract val viewModel: BaseViewModel

    protected val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(res)

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        title = pageTitle

        viewModel.disposeBag = compositeDisposable
        viewModel.loadingErrorSubs = Consumer<ResultEvent<*>> { state ->
            when (state) {
                is ResultEvent.Success -> {
                }
                is ResultEvent.Error -> {
                }
                is ResultEvent.InProgress -> {
                    toast("Loading...")
                }
            }
        }

        viewCreated()
        observe()
    }

    abstract fun viewCreated()

    abstract fun observe()

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
            compositeDisposable.clear()
        }
        super.onDestroy()
    }
}

fun AppCompatActivity.startActivity(cls: Class<*>) {
    this.startActivity(Intent(this, cls))
}