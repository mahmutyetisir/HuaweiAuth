package com.dtsedemo.mtwoauth.ui.email

import android.widget.Toast
import com.dtsedemo.mtwoauth.R
import com.dtsedemo.mtwoauth.common.click
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.ui.base.BaseActivity
import com.dtsedemo.mtwoauth.viewmodel.EmailViewModel
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.android.synthetic.main.activity_mail_login.*
import org.koin.android.ext.android.inject

class MailLoginActivity : BaseActivity() {
    override val pageTitle: String = "Email ile Giriş Yap"
    override val res: Int = R.layout.activity_mail_login
    override val viewModel: EmailViewModel by inject()


    override fun viewCreated() {
        btn_email_login?.click(compositeDisposable) {
            edtxt_email?.text?.toString()?.let { email ->
                edtxt_email_password?.text?.toString()?.let { password ->
                    viewModel.login(email, password)
                }
            }
        }
    }

    override fun observe() {
        viewModel.loginSubscription = Consumer {
            when (it) {
                is ResultEvent.Success -> {
                    Toast.makeText(this, "Giriş Yapıldı", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
                is ResultEvent.Error -> {
                    Toast.makeText(
                        this,
                        "Giriş yapılamadı hata: ${it.exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


}