package com.dtsedemo.mtwoauth.ui.phone

import com.dtsedemo.mtwoauth.R
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.model.User
import com.dtsedemo.mtwoauth.ui.base.BaseWelcomeActivity
import com.dtsedemo.mtwoauth.ui.base.startActivity
import com.dtsedemo.mtwoauth.viewmodel.PhoneViewModel
import io.reactivex.rxjava3.functions.Consumer
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomePhoneActivity : BaseWelcomeActivity() {

    override val containerID: Int = R.id.container_phone_welcome_fragment
    override val pageTitle: String = "Telefon Numrası"
    override val res: Int = R.layout.activity_welcome_phone
    override val viewModel: PhoneViewModel by viewModel()
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
        startActivity(LoginPhoneActivity::class.java)
    }

    override fun registerButtonClick() {
        startActivity(RegisterPhoneActivity::class.java)
    }

}