package com.dtsedemo.mtwoauth.ui.base.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dtsedemo.mtwoauth.R
import com.dtsedemo.mtwoauth.model.User
import kotlinx.android.synthetic.main.fragment_profile.*

const val PARAM_USER = "user"

class ProfileFragment : Fragment() {
    private var logOutCallback: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            (it.getSerializable(PARAM_USER) as User).let { user ->
                txt_user.text =
                    "phone: ${user.phoneNumber}  - uid: ${user.uid}  - displayName: ${user.displayName}"
            }
        }

        btn_logout.setOnClickListener {
            logOutCallback.invoke()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(user: User, logOutCallback: () -> Unit) =
            ProfileFragment().apply {
                this.logOutCallback = logOutCallback
                arguments = Bundle().apply {
                    putSerializable(PARAM_USER, user)
                }
            }
    }
}