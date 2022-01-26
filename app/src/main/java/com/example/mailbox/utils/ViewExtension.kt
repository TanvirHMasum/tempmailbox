package com.example.mailbox.utils

import android.content.Context
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.isVisible() = visibility == View.VISIBLE

fun View.isGone() = visibility == View.GONE


fun View.fadeIn(duration: Int = 400) {
    clearAnimation()
    val alphaAnimation = AlphaAnimation(this.alpha, 1.0f)
    alphaAnimation.duration = duration.toLong()
    startAnimation(alphaAnimation)
}

fun View.showSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun EditText.textToString(): String = this.text.toString()

fun EditText.checkIsEmpty(): Boolean = text == null || "" == textToString() || text.toString().equals("null", ignoreCase = true)

fun EditText.showError(error: String) {
    this.error = error
    this.showSoftKeyboard()
}