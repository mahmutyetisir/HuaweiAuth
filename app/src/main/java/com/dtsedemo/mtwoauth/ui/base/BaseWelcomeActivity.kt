package com.dtsedemo.mtwoauth.ui.base

import androidx.fragment.app.Fragment
import com.dtsedemo.mtwoauth.model.User
import com.dtsedemo.mtwoauth.ui.base.fragments.ProfileFragment
import com.dtsedemo.mtwoauth.ui.base.fragments.WelcomeFragment
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.auth.AGConnectUser

abstract class BaseWelcomeActivity : BaseActivity() {

    abstract val containerID: Int
    abstract val logOutCallback: () -> Unit

    override fun onResume() {
        super.onResume()
        if (getCurrentUser() == null) {
            showWelcomeFragment()
        } else {
            showProfileFragment()
        }
    }

    abstract fun getCurrentUser(): User?

    fun showProfileFragment() {
        getCurrentUser()?.let { user ->
            changeFragment(ProfileFragment.newInstance(user) {
                logOutCallback.invoke()
            })
        }
    }

    open fun showWelcomeFragment() {
        changeFragment(WelcomeFragment.newInstance({
            signInButtonClick()
        }, {
            registerButtonClick()
        }))
    }

    fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            .replace(containerID, fragment)
            .commit()
    }

    abstract fun signInButtonClick()
    abstract fun registerButtonClick()
}