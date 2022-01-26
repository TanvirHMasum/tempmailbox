@file:Suppress("DEPRECATION", "unused")

package com.example.mailbox

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.mailbox.activity.ContactActivity
import com.example.mailbox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.animTV.animation = AnimationUtils.loadAnimation(
            this,
            R.anim.bottom_animation
        )
        Handler().postDelayed({
            Intent(this@MainActivity, ContactActivity::class.java).also {
                startActivity(it)
                this.finish()
            }
        }, 3500)
    }
}
