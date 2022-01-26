package com.example.mailbox.activity

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.mailbox.R
import com.example.mailbox.utils.changeToolbarFont

open class AppBaseActivity : AppCompatActivity() {
    private var progressDialog: Dialog? = null

    fun setToolbarWithoutBackButton(mToolbar: Toolbar) {
        setSupportActionBar(mToolbar)
    }

    fun setToolbar(mToolbar: Toolbar) {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mToolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace)
        mToolbar.changeToolbarFont()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (progressDialog == null) {
            progressDialog = Dialog(this)
            progressDialog?.window?.setBackgroundDrawable(ColorDrawable(0))
            progressDialog?.setContentView(R.layout.dialog_loading)
        }
        setContentView(R.layout.activity_app_base)
    }

    fun showProgress(show: Boolean) {
        when {
            show -> {
                if (!isFinishing && !progressDialog!!.isShowing) {
                    progressDialog?.setCanceledOnTouchOutside(false)
                    progressDialog?.show()
                }
            }
            else -> try {
                if (progressDialog?.isShowing!! && !isFinishing) {
                    progressDialog?.dismiss()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun hideProgress(show: Boolean) {
        showProgress(false)
    }
}