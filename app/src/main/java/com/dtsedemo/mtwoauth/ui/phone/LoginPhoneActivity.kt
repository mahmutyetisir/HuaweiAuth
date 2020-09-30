package com.dtsedemo.mtwoauth.ui.phone

import com.dtsedemo.mtwoauth.R
import com.dtsedemo.mtwoauth.common.click
import com.dtsedemo.mtwoauth.common.toast
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.ui.base.BaseActivity
import com.dtsedemo.mtwoauth.viewmodel.PhoneViewModel
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.android.synthetic.main.activity_login_phone.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginPhoneActivity : BaseActivity() {
    override val pageTitle: String = "Telefon Numarası ile Giriş Yap"
    override val res: Int = R.layout.activity_login_phone
    override val viewModel: PhoneViewModel by viewModel()

    override fun viewCreated() {
        btn_phone_login?.click(compositeDisposable) {
            edtxt_phone_number?.text?.toString()?.let { phoneNumber ->
                edtxt_password?.text?.toString()?.let { password ->
                    viewModel.login(phoneNumber, password)
                }
            }
        }
    }

    override fun observe() {
        viewModel.loginSubscription = Consumer {
            when (it) {
                is ResultEvent.Success -> {
                    toast("Giriş Yapıldı")
                    onBackPressed()
                }
                is ResultEvent.Error -> {
                    toast("Giriş yapılamadı hata: ${it.exception.message}")
                }
            }
        }
    }

}