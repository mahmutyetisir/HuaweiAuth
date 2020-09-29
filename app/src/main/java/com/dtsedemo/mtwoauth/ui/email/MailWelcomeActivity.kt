package com.dtsedemo.mtwoauth.ui.email

import com.dtsedemo.mtwoauth.R
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.model.User
import com.dtsedemo.mtwoauth.ui.base.BaseWelcomeActivity
import com.dtsedemo.mtwoauth.ui.base.startActivity
import com.dtsedemo.mtwoauth.viewmodel.EmailViewModel
import io.reactivex.rxjava3.functions.Consumer
import org.koin.androidx.viewmodel.ext.android.viewModel

class MailWelcomeActivity : BaseWelcomeActivity() {
    override val pageTitle: String = "Email"
    override val res: Int = R.layout.activity_mail_welcome
    override val viewModel: EmailViewModel by viewModel()
    override val containerID: Int = R.id.container_email_welcome_fragment
    override val logOutCallback: () -> Unit = {
        viewModel.logout()
    }

    override fun viewCreated() {

    }

    override fun observe() {
        viewModel.logoutSubscription = Consumer {
            when (it) {
                is ResultEvent.Success -> {
                    showWelcomeFragment()
                }
                is ResultEvent.Error -> {
                }
            }
        }
    }

    override fun getCurrentUser(): User? {
        return viewModel.getUser()
    }

    override fun signInButtonClick() {
        startActivity(MailLoginActivity::class.java)
    }

    override fun registerButtonClick() {
        startActivity(MailRegisterActivity::class.java)
    }

}