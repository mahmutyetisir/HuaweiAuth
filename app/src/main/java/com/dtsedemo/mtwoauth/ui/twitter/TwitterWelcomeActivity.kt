package com.dtsedemo.mtwoauth.ui.twitter

import android.content.Intent
import android.util.Log
import com.dtsedemo.mtwoauth.R
import com.dtsedemo.mtwoauth.common.toast
import com.dtsedemo.mtwoauth.model.ResultEvent
import com.dtsedemo.mtwoauth.model.User
import com.dtsedemo.mtwoauth.ui.base.BaseWelcomeActivity
import com.dtsedemo.mtwoauth.viewmodel.TwitterViewModel
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import io.reactivex.rxjava3.functions.Consumer
import org.koin.androidx.viewmodel.ext.android.viewModel

class TwitterWelcomeActivity : BaseWelcomeActivity() {
    override val pageTitle: String = "Twitter"
    override val res: Int = R.layout.activity_twitter_welcome
    override val viewModel: TwitterViewModel by viewModel()
    override val containerID: Int = R.id.container_twitter_welcome_fragment
    override val logOutCallback: () -> Unit = {
        viewModel.logout()
    }

    private var twitterLoginButton: TwitterLoginButton? = null
    private var token: String? = null
    private var secret: String? = null

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
        changeFragment(TwitterWelcomeFragment.newInstance({
            twitterLoginButton = it
        }, {
            toast(it?.message)
        }) { token, secret ->
            this.token = token
            this.secret = secret
            signInButtonClick()
        })
    }

    override fun getCurrentUser(): User? {
        return viewModel.getUser()
    }

    override fun signInButtonClick() {
        token?.let { currentToken ->
            secret?.let { currentSecret ->
                viewModel.login(currentToken, currentSecret)
            }
        }
    }

    override fun registerButtonClick() {}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        twitterLoginButton?.onActivityResult(requestCode, resultCode, data)
        Log.d("twitterlogin", "$requestCode --- $resultCode --- ${data?.extras}")
    }

}