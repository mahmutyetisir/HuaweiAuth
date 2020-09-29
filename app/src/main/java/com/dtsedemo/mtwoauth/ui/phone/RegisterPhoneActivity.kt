package com.dtsedemo.mtwoauth.ui.phone

import android.content.Intent
import android.widget.Toast
import com.dtsedemo.mtwoauth.R
import com.dtsedemo.mtwoauth.common.click
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.ui.base.BaseActivity
import com.dtsedemo.mtwoauth.viewmodel.PhoneViewModel
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.android.synthetic.main.activity_register_phone.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterPhoneActivity : BaseActivity() {
    override val pageTitle: String = "Telefon Numarası ile Kayıt Ol"
    override val res: Int = R.layout.activity_register_phone
    override val viewModel: PhoneViewModel by viewModel()

    override fun viewCreated() {
        btn_send_code?.click(compositeDisposable) {
            viewModel.register(edtxt_phone_number.text.toString())
        }
    }

    override fun observe() {
        viewModel.registerSubscription = Consumer {
            when (it) {
                is ResultEvent.Success -> {
                    startActivityForResult(
                        VerifyPhoneActivity.getVerifyIntent(
                            this,
                            edtxt_phone_number.text.toString(),
                            edtxt_password.text.toString()
                        ), 22
                    )
                }
                is ResultEvent.Error -> {
                    Toast.makeText(
                        MainActivity@ this,
                        "kod gönderilemedi ${it.exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
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