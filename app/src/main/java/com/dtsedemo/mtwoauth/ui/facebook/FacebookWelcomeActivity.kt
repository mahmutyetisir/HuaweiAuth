package com.dtsedemo.mtwoauth.ui.facebook

import android.content.Intent
import com.dtsedemo.mtwoauth.R
import com.dtsedemo.mtwoauth.common.toast
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.model.User
import com.dtsedemo.mtwoauth.ui.base.BaseWelcomeActivity
import com.dtsedemo.mtwoauth.viewmodel.FacebookViewModel
import com.facebook.CallbackManager
import io.reactivex.rxjava3.functions.Consumer
import org.koin.androidx.viewmodel.ext.android.viewModel


class FacebookWelcomeActivity : BaseWelcomeActivity() {
    override val pageTitle: String = "Facebook"
    override val res: Int = R.layout.activity_facebook_welcome
    override val viewModel: FacebookViewModel by viewModel()
    override val containerID: Int = R.id.container_facebook_welcome_fragment
    override val logOutCallback: () -> Unit = {
        viewModel.logout()
    }

    private var faceBookCallbackManager: CallbackManager? = null
    private var accessToken: String? = null

    override fun viewCreated() {

    }

    override fun observe() {
        viewModel.loginSubscription = Consumer {
            when (it) {
                is ResultEvent.Success -> {
                    toast("Giriş Yapıldı")
                    showProfileFragment()
                }
                is ResultEvent.Error -> {
                    toast(it.exception.message)
                }
            }
        }
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

    override fun showWelcomeFragment() {
        changeFragment(FacebookWelcomeFragment.newInstance { callbackManager, accessToken ->
            this.faceBookCallbackManager = callbackManager
            this.accessToken = accessToken
            signInButtonClick()
        })
    }

    override fun getCurrentUser(): User? {
        return viewModel.getUser()
    }

    override fun signInButtonClick() {
        accessToken?.let {
            viewModel.login(it)
        }
    }

    override fun registerButtonClick() {
        // Facebook ile kayıt olma işlemi direk sdk tarafında olduğu için bu metodun gövdesi boş bırakıldı
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        faceBookCallbackManager?.onActivityResult(requestCode, resultCode, data)
    }
}