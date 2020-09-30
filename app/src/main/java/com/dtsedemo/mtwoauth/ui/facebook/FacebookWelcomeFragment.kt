package com.dtsedemo.mtwoauth.ui.facebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dtsedemo.mtwoauth.R
import com.dtsedemo.mtwoauth.common.toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.fragment_facebook_welcome.*

class FacebookWelcomeFragment : Fragment() {
    private var loginCallback: (callbackManager: CallbackManager, accessToken: String?) -> Unit =
        { _, _ -> }
    private val mCallbackManager = CallbackManager.Factory.create()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_facebook_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_facebook_login.registerCallback(
            mCallbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    loginCallback.invoke(mCallbackManager, result?.accessToken?.token)
                }

                override fun onCancel() {
                    context?.toast("cancel login")
                }

                override fun onError(error: FacebookException?) {
                    context?.toast("exception ${error?.message}")
                }

            })
    }

    companion object {
        @JvmStatic
        fun newInstance(loginCallback: (callbackManager: CallbackManager, accessToken: String?) -> Unit) =
            FacebookWelcomeFragment().apply {
                this.loginCallback = loginCallback
            }
    }
}