package com.dtsedemo.mtwoauth.ui.email

import android.content.Intent
import android.widget.Toast
import com.dtsedemo.mtwoauth.R
import com.dtsedemo.mtwoauth.common.click
import com.dtsedemo.mtwoauth.common.toast
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.ui.base.BaseActivity
import com.dtsedemo.mtwoauth.viewmodel.EmailViewModel
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.android.synthetic.main.activity_mail_register.*
import org.koin.android.ext.android.inject

class MailRegisterActivity : BaseActivity() {
    override val pageTitle: String = "Email ile Kayıt Ol"
    override val res: Int = R.layout.activity_mail_register
    override val viewModel: EmailViewModel by inject()


    override fun viewCreated() {
        btn_email_send_code.click(compositeDisposable) {
            viewModel.register(edtxt_email.text.toString())
        }
    }

    override fun observe() {
        viewModel.registerSubscription = Consumer {
            when (it) {
                is ResultEvent.Success -> {
                    startActivityForResult(
                        MailVerifyActivity.getVerifyIntent(
                            this,
                            edtxt_email.text.toString(),
                            edtxt_email_password.text.toString()
                        ), 22
                    )
                }
                is ResultEvent.Error -> {
                    toast("kod gönderilemedi ${it.exception.message}")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 22 && resultCode == RESULT_OK) {
            data?.let {
                if (it.getBooleanExtra("register_state", false)) {
                    onBackPressed()
                }
            }
        }
    }
}