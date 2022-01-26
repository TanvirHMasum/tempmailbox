package com.example.mailbox.fragment

import android.annotation.SuppressLint
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.mailbox.R
import com.example.mailbox.activity.AppBaseActivity

abstract class BaseFragment : Fragment(), View.OnFocusChangeListener {
    @SuppressLint("UseCompatLoadingForDrawables", "NewApi")
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {
            (v as EditText).setTextColor(requireActivity().getColor(R.color.colorPrimaryDark))
            v.background = requireActivity().getDrawable(R.drawable.bg_ractangle_rounded_active)
        } else {
            (v as EditText).setTextColor(requireActivity().getColor(R.color.textColorPrimary))
            v.background = requireActivity().getDrawable(R.drawable.bg_ractangle_rounded_inactive)
        }
    }

    fun hideProgress() {
        if (activity != null)
            (requireActivity() as AppBaseActivity).showProgress(false)
    }

    fun showProgress() {
        if (activity != null)
            (requireActivity() as AppBaseActivity).showProgress(true)
    }

    object BiggerDotTransformation : PasswordTransformationMethod() {

        override fun getTransformation(source: CharSequence, view: View): CharSequence {
            return PasswordCharSequence(super.getTransformation(source, view))
        }

        private class PasswordCharSequence(val transformation: CharSequence) : CharSequence by transformation {
            override fun get(index: Int): Char = if (transformation[index] == DOT) {
                BIGGER_DOT
            } else {
                transformation[index]
            }
        }

        private const val DOT = '\u2022'
        private const val BIGGER_DOT = '●'
    }
}