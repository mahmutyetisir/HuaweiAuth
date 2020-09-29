package com.dtsedemo.mtwoauth.ui

import com.dtsedemo.mtwoauth.R
import com.dtsedemo.mtwoauth.common.addTo
import com.dtsedemo.mtwoauth.common.click
import com.dtsedemo.mtwoauth.ui.base.BaseActivity
import com.dtsedemo.mtwoauth.ui.base.startActivity
import com.dtsedemo.mtwoauth.ui.email.MailWelcomeActivity
import com.dtsedemo.mtwoauth.ui.facebook.FacebookWelcomeActivity
import com.dtsedemo.mtwoauth.ui.phone.WelcomePhoneActivity
import com.dtsedemo.mtwoauth.ui.twitter.TwitterWelcomeActivity
import com.dtsedemo.mtwoauth.viewmodel.MainViewModel
import com.dtsedemo.mtwoauth.viewmodel.PhoneViewModel
import com.jakewharton.rxbinding4.view.clicks
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    override val pageTitle: String = "Huawei Auth"
    override val res: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()

    override fun viewCreated() {
        btn_phone_register.click(compositeDisposable) {
            startActivity(WelcomePhoneActivity::class.java)
        }

        btn_mail_register.click(compositeDisposable) {
            startActivity(MailWelcomeActivity::class.java)
        }

        btn_facebook_register.click(compositeDisposable) {
            startActivity(FacebookWelcomeActivity::class.java)
        }

        btn_twitter_register.click(compositeDisposable) {
            startActivity(TwitterWelcomeActivity::class.java)
        }
    }

    override fun observe() {

    }

}

