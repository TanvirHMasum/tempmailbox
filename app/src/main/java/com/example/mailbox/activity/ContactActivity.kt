package com.example.mailbox.activity

import com.example.mailbox.R
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.mailbox.databinding.ActivityContactBinding
import java.util.*

class ContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactBinding


    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val connectivityManager =
            baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if (networkInfo != null && networkInfo.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            Intent(this@ContactActivity, HomeActivity::class.java).also {
                startActivity(it)
                //this.finish()
            }

            //nextBtn.visibility
        } else {
            callWebservice()
        }

    }

    private fun callWebservice() {
        val dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.dialog_internet_connection)

        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        val buttonSetting = dialog.findViewById<View>(R.id.button_setting) as TextView
        val buttonTryAgain = dialog.findViewById<View>(R.id.button_tryAgain) as TextView

        buttonSetting.setOnClickListener { sentWifiSettings(this) }

        buttonTryAgain.setOnClickListener {

            dialog.dismiss()
            // set delay for smooth animation
            val mainExecutor = ContextCompat.getMainExecutor(this)
            mainExecutor.execute {
                startActivity(Intent(this, this::class.java))
                this.finish()
            }
        }

        dialog.show()
    }

    private fun sentWifiSettings(context: Context?) {
        context?.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
    }
}