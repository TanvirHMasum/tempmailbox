package com.example.mailbox.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.mailbox.R
import com.google.android.material.snackbar.Snackbar

inline fun <T : View> T.onClick(crossinline func: T.() -> Unit) = setOnClickListener { func() }

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

fun Context.color(color: Int): Int = ContextCompat.getColor(this, color)

fun runDelayed(delayMillis: Long = 200, action: () -> Unit) =
    Handler().postDelayed(Runnable(action), delayMillis)

fun Toolbar.changeToolbarFont() {
    for (i in 0 until childCount) {
        val view = getChildAt(i)
        if (view is TextView && view.text == title) {
            break
        }
    }
}

inline fun <reified T : Any> Activity.launchActivityWithNewTask() {
    launchActivity<T> {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
}

inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) =
    supportFragmentManager.inTransaction { replace(frameId, fragment) }

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) =
    supportFragmentManager.inTransaction { add(frameId, fragment) }

fun AppCompatActivity.removeFragment(fragment: Fragment) =
    supportFragmentManager.inTransaction { remove(fragment) }

fun AppCompatActivity.showFragment(fragment: Fragment) = supportFragmentManager.inTransaction {
    setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
    show(fragment)
}

fun AppCompatActivity.hideFragment(fragment: Fragment) = supportFragmentManager.inTransaction {
    hide(fragment)
}

fun ImageView.applyColorFilter(color: Int) {
    drawable.let { DrawableCompat.wrap(it) }.let {
        DrawableCompat.setTint(it, color)
    }
}

fun runDelayedOnUiThread(delayMillis: Long, action: () -> Unit) =
    Handler(Looper.getMainLooper()).postDelayed(Runnable(action), delayMillis)


fun Activity.snackBar(msg: String, length: Int = Snackbar.LENGTH_SHORT) =
    Snackbar.make(findViewById(android.R.id.content), msg, length).setTextColor(Color.WHITE).show()

fun Fragment.snackBar(msg: String) = activity!!.snackBar(msg)

fun Activity.snackBarError(msg: String) {
    val snackBar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
    val sbView = snackBar.view
    sbView.setBackgroundColor(resources.getColor(R.color.purple_700));snackBar.setTextColor(
        Color.WHITE
    );snackBar.show()
}


fun Activity.showPermissionAlert(view: View) {
    val snackBar = Snackbar.make(
        view,
        getString(R.string.error_permission_required),
        Snackbar.LENGTH_INDEFINITE
    )
    val sbView = snackBar.view
    snackBar.setAction("Enable") {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
        snackBar.dismiss()
    }
    sbView.setBackgroundColor(resources.getColor(R.color.purple_700));snackBar.setTextColor(
        Color.WHITE
    );snackBar.show()
}

fun Fragment.getFragAlertDialog(aMsgText: String, aTitleText: String = getString(R.string.dialog_title), aPositiveText: String = getString(R.string.ok_text), onPositiveClick: (dialog: DialogInterface, Int) -> Unit): AlertDialog {
    val builder = AlertDialog.Builder(requireContext())
    builder.setTitle(aTitleText)
    builder.setIcon(R.drawable.icon_success)
    builder.setMessage(aMsgText)
    builder.setCancelable(false)
    builder.setPositiveButton(aPositiveText) { dialog, which ->
        onPositiveClick(dialog, which)
    }
    return builder.create()
}


fun Fragment.getFragAlertDialog2(aMsgText: String, aTitleText: String = getString(R.string.dialog_title), aPositiveText: String = getString(R.string.yes_text), aNegativeText: String = getString(R.string.no_text), onPositiveClick: (dialog: DialogInterface, Int) -> Unit, onNegativeClick: (dialog: DialogInterface, Int) -> Unit): AlertDialog {
    val builder = AlertDialog.Builder(requireContext())
    builder.setTitle(aTitleText)
    builder.setMessage(aMsgText)
    builder.setPositiveButton(aPositiveText) { dialog, which ->
        onPositiveClick(dialog, which)
    }
    builder.setNegativeButton(aNegativeText) { dialog, which ->
        onNegativeClick(dialog, which)
    }
    return builder.create()
}


fun Activity.getAlertDialog(aMsgText: String, aTitleText: String = getString(R.string.dialog_title), aPositiveText: String = getString(R.string.yes_text), aNegativeText: String = getString(R.string.no_text), onPositiveClick: (dialog: DialogInterface, Int) -> Unit, onNegativeClick: (dialog: DialogInterface, Int) -> Unit): AlertDialog {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(aTitleText)
    builder.setMessage(aMsgText)
    builder.setPositiveButton(aPositiveText) { dialog, which ->
        onPositiveClick(dialog, which)
    }
    builder.setNegativeButton(aNegativeText) { dialog, which ->
        onNegativeClick(dialog, which)
    }
    return builder.create()
}
