package com.dtsedemo.mtwoauth.ui.base.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dtsedemo.mtwoauth.R
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment() {
    private var loginCallback: () -> Unit = {}
    private var registerCallback: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_sign_in.setOnClickListener {
            loginCallback.invoke()
        }

        btn_regiser.setOnClickListener {
            registerCallback.invoke()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(loginCallback: () -> Unit, registerCallback: () -> Unit) =
            WelcomeFragment().apply {
                this.loginCallback = loginCallback
                this.registerCallback = registerCallback
            }
    }
}