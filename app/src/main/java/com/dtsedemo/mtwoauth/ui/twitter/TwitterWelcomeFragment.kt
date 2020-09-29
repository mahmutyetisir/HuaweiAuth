package com.dtsedemo.mtwoauth.ui.twitter

import android.os.Bundle
import android.provider.UserDictionary.Words.APP_ID
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dtsedemo.mtwoauth.R
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import kotlinx.android.synthetic.main.fragment_twitter_welcome.*


class TwitterWelcomeFragment : Fragment() {
    private var buttonCallback: (twitterLoginButton: TwitterLoginButton?) -> Unit = {}
    private var twitterCallback: (token: String?, secret: String?) -> Unit =
        { token, secret -> }
    private var failureCallback: (exception: TwitterException?) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_twitter_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val authConfig = TwitterAuthConfig(APP_ID, "ssadasd")
        val twitterConfig =
            TwitterConfig.Builder(this.context).twitterAuthConfig(authConfig).build()
        Twitter.initialize(twitterConfig)

        buttonCallback.invoke(btn_twitter_login)

        btn_twitter_login.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                twitterCallback.invoke(
                    result?.data?.authToken?.token,
                    result?.data?.authToken?.secret
                )
            }

            override fun failure(exception: TwitterException?) {
                failureCallback.invoke(exception)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(
            buttonCallback: (twitterLoginButton: TwitterLoginButton?) -> Unit,
            failureCallback: (exception: TwitterException?) -> Unit,
            successCallback: (token: String?, secret: String?) -> Unit
        ) =
            TwitterWelcomeFragment().apply {
                this.buttonCallback = buttonCallback
                this.twitterCallback = successCallback
                this.failureCallback = failureCallback
            }
    }
}