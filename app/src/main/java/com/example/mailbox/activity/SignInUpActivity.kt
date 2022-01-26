package com.example.mailbox.activity

import android.os.Bundle
import android.widget.FrameLayout
import com.example.mailbox.R
import com.example.mailbox.fragment.SignInFragment
import com.example.mailbox.utils.addFragment
import com.example.mailbox.utils.fadeIn
import com.example.mailbox.utils.replaceFragment

class SignInUpActivity : AppBaseActivity() {
    private val signInFragment: SignInFragment = SignInFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_up)

        if (supportFragmentManager.findFragmentById(R.id.container) != null) {
            supportFragmentManager.beginTransaction()
                .remove(supportFragmentManager.findFragmentById(R.id.container)!!).commit()
        }

        loadSignInFragment()
    }

    fun loadSignInFragment() {
        if (signInFragment.isAdded) {
            replaceFragment(signInFragment, R.id.fragmentContainer)
            findViewById<FrameLayout>(R.id.fragmentContainer).fadeIn(500)
        } else {
            addFragment(signInFragment, R.id.fragmentContainer)
        }
    }

}