package com.dtsedemo.mtwoauth.ui.phone

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.dtsedemo.mtwoauth.R
import com.dtsedemo.mtwoauth.common.addTo
import com.dtsedemo.mtwoauth.common.click
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.ui.base.BaseActivity
import com.dtsedemo.mtwoauth.viewmodel.PhoneViewModel
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.auth.PhoneUser
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.android.synthetic.main.activity_verify_phone.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerifyPhoneActivity : BaseActivity() {

    override val pageTitle: String = "SMS Doğrulama"
    override val res: Int = R.layout.activity_verify_phone
    override val viewModel: PhoneViewModel by viewModel()

    companion object {
        const val VERFIY_PHONE_NUMBER = "user_phone_number"
        const val VERFIY_PASSWORD = "user_password"

        fun getVerifyIntent(
            context: Context,
            phoneNumber: String,
            password: String
        ): Intent {
            return Intent(context, VerifyPhoneActivity::class.java).apply {
                putExtra(VERFIY_PHONE_NUMBER, phoneNumber)
                putExtra(VERFIY_PASSWORD, password)
            }
        }

        fun getExtras(intent: Intent, callback: (String, String) -> Unit) {
            val phone = intent.extras?.getString(VERFIY_PHONE_NUMBER)
            val pass = intent.extras?.getString(VERFIY_PASSWORD)
            phone?.let { p ->
                pass?.let {
                    callback.invoke(p, it)
                }
            }

        }
    }

    override fun viewCreated() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        btn_verify_code?.click(compositeDisposable) {
            getExtras(intent) { phone, password ->
                viewModel.verifyCode(phone, password, edtxt_verify_code.text.toString())
            }
        }
    }

    override fun observe() {
        viewModel.verifySubscription = Consumer {
            when (it) {
                is ResultEvent.Success -> {
                    val intent = intent
                    intent.putExtra("register_state", true)
                    setResult(RESULT_OK, intent)
                    onBackPressed()
                    Toast.makeText(
                        VerifyActivity@ this,
                        "Kullanıcı oluşturuldu",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is ResultEvent.Error -> {
                    Toast.makeText(
                        VerifyActivity@ this,
                        "Kullanıcı oluşturulamadı ${it.exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}