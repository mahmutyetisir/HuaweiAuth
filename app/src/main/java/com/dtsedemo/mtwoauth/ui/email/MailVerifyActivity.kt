package com.dtsedemo.mtwoauth.ui.email

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.dtsedemo.mtwoauth.R
import com.dtsedemo.mtwoauth.common.click
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.ui.base.BaseActivity
import com.dtsedemo.mtwoauth.viewmodel.EmailViewModel
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.android.synthetic.main.activity_mail_verify.*
import org.koin.android.ext.android.inject

class MailVerifyActivity : BaseActivity() {
    override val pageTitle: String = "Email Doğrulaması"
    override val res: Int = R.layout.activity_mail_verify
    override val viewModel: EmailViewModel by inject()

    companion object {
        const val VERFIY_EMAIL = "user_email"
        const val VERFIY_PASSWORD = "user_password"

        fun getVerifyIntent(
            context: Context,
            email: String,
            password: String
        ): Intent {
            return Intent(context, MailVerifyActivity::class.java).apply {
                putExtra(VERFIY_EMAIL, email)
                putExtra(VERFIY_PASSWORD, password)
            }
        }

        fun getExtras(intent: Intent, callback: (String, String) -> Unit) {
            val email = intent.extras?.getString(VERFIY_EMAIL)
            val pass = intent.extras?.getString(VERFIY_PASSWORD)
            email?.let { p ->
                pass?.let {
                    callback.invoke(p, it)
                }
            }

        }
    }

    override fun viewCreated() {
        btn_email_verify_code.click(compositeDisposable) {
            getExtras(intent) { email, password ->
                viewModel.verifyCode(email, password, edtxt_email_verify_code.text.toString())
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
                        MailVerifyActivity@ this,
                        "Kullanıcı oluşturuldu",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is ResultEvent.Error -> {
                    Toast.makeText(
                        MailVerifyActivity@ this,
                        "Kullanıcı oluşturulamadı ${it.exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}