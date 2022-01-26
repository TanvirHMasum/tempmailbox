package com.example.mailbox.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.mailbox.R
import com.example.mailbox.databinding.ActivityHomeBinding
import com.example.mailbox.fragment.*
import com.example.mailbox.service.PreferencesProvider
import com.example.mailbox.utils.*

class HomeActivity : AppBaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var preferenceProvider: PreferencesProvider

    //region Variables
    private lateinit var mHomeFragment: Fragment

    private val mCreateAccFragment = CreateAccFragment()
    private val mSendMailFragment = SendMailFragment()
    private val mInboxFragment = InboxFragment()
    private val mAccountFragment = AccountFragment()
    private var selectedFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setToolbar(binding.main.toolbarLay.toolbar)

        setContentView(view)

        title = getString(R.string.app_name)


        preferenceProvider = PreferencesProvider(applicationContext)

        if (supportFragmentManager.findFragmentById(R.id.container) != null) {
            supportFragmentManager.beginTransaction()
                .remove(supportFragmentManager.findFragmentById(R.id.container)!!).commit()
        }

        mHomeFragment = HomeFragment()

        setToolbar(binding.main.toolbarLay.toolbar);
        setUpDrawerToggle(); loadHomeFragment(); setListener()

        if (preferenceProvider.getBoolean(SharedPrefData.IS_LOGGED_IN)) {
            //setWishCount(); setCartCountFromPref()
            binding.layoutSidebar.tvLogout.text = getString(R.string.logout)
            binding.layoutSidebar.tvLogout.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.ic_logout,
                0,
                0,
                0
            )
        } else {
            binding.layoutSidebar.tvLogout.text = getString(R.string.login)
            binding.layoutSidebar.tvLogout.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.ic_login_round,
                0,
                0,
                0
            )
        }
    }

    private fun setUpDrawerToggle() {
        val toggle = object : ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.main.toolbarLay.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)

                binding.main.appBarDashboard.translationX = slideOffset * drawerView.width

                (binding.drawerLayout).bringChildToFront(drawerView)
                (binding.drawerLayout).requestLayout()
            }
        }

        toggle.setToolbarNavigationClickListener {
            if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        toggle.isDrawerIndicatorEnabled = false
        toggle.setHomeAsUpIndicator(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_drawer,
                theme
            )
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setListener() {
        binding.main.llBottom.llHome.onClick {
            closeDrawer()
            enable(binding.main.llBottom.ivHome)
            loadFragment(mHomeFragment)
            title = getString(R.string.domain)
            /*if(mCartFragment.isAdded){
                removeFragment(mCartFragment)
            }*/
        }

        binding.main.llBottom.llCreateAcc.onClick {
            /*if (!preferenceProvider.getBoolean(SharedPrefData.IS_LOGGED_IN)) {
                launchActivity<SignInUpActivity>(); return@onClick
            }*/
            closeDrawer()
            enable(binding.main.llBottom.ivCreateAcc)
            loadFragment(mCreateAccFragment)
            title = getString(R.string.create_account)
        }

        binding.main.llBottom.llCart.onClick {
            if (!preferenceProvider.getBoolean(SharedPrefData.IS_LOGGED_IN)) {
                launchActivity<SignInUpActivity>(); return@onClick
            }
            closeDrawer()
            //enable(menuCartBinding.ivCart)
            //tvNotificationCount.hide()
            loadFragment(mInboxFragment)
            title = getString(R.string.inbox_mail)
        }

        binding.main.llBottom.llAccount.onClick {
            if (!preferenceProvider.getBoolean(SharedPrefData.IS_LOGGED_IN)) {
                launchActivity<SignInUpActivity>(); return@onClick
            }
            closeDrawer()
            enable(binding.main.llBottom.ivAccount)
            loadFragment(mAccountFragment)
            title = getString(R.string.account)
        }


        binding.main.llBottom.llSendMail.onClick {
            if (!preferenceProvider.getBoolean(SharedPrefData.IS_LOGGED_IN)) {
                launchActivity<SignInUpActivity>(); return@onClick
            }
            closeDrawer()
            enable(binding.main.llBottom.ivSendMail)
            loadFragment(mSendMailFragment)
            title = getString(R.string.account)
        }


        binding.layoutSidebar.tvLogout.onClick {
            if (preferenceProvider.getBoolean(SharedPrefData.IS_LOGGED_IN)) {
                val dialog = getAlertDialog(
                    getString(R.string.logout_confirmation_text),
                    onPositiveClick = { _, _ ->
                        preferenceProvider.clear()
                        launchActivityWithNewTask<HomeActivity>()
                        super.onBackPressed()
                        //clearLoginPref()
                        //recreate()
                    },
                    onNegativeClick = { dialog, _ ->
                        dialog.dismiss()
                    })
                dialog.show()
                closeDrawer()
            } else {
                launchActivity<SignInUpActivity>()
            }
        }

        binding.layoutSidebar.ivCloseDrawer.onClick { closeDrawer() }
    }

    private fun closeDrawer() {
        if (binding.drawerLayout.isDrawerOpen(binding.llLeftDrawer)) runDelayed(50) {
            binding.drawerLayout.closeDrawer(
                binding.llLeftDrawer
            )
        }
    }

    override fun onBackPressed() {
        when {
            binding.drawerLayout.isDrawerOpen(GravityCompat.START) -> binding.drawerLayout.closeDrawer(
                GravityCompat.START
            )
            !mHomeFragment.isVisible -> loadHomeFragment()
            else -> super.onBackPressed()
        }
    }

    private fun loadFragment(aFragment: Fragment) {
        if (selectedFragment != null) {
            if (selectedFragment == aFragment) return
            hideFragment(selectedFragment!!)
        }
        if (aFragment.isAdded) {
            showFragment(aFragment)
        } else {
            addFragment(aFragment, R.id.container)
        }
        selectedFragment = aFragment
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun enable(aImageView: ImageView?) {
        disableAll()
        //showCartCount()
        aImageView?.background = getDrawable(R.drawable.bg_circle_primary_light)
        aImageView?.applyColorFilter(color(R.color.colorPrimary))
    }

    private fun disableAll() {
        disable(binding.main.llBottom.ivHome)
        disable(binding.main.llBottom.ivCreateAcc)
        disable(binding.main.llBottom.ivSendMail)
        disable(binding.main.llBottom.ivAccount)
    }

    private fun disable(aImageView: ImageView?) {
        aImageView?.background = null
        aImageView?.applyColorFilter(color(R.color.textColorSecondary))
    }

    fun loadHomeFragment() {
        enable(binding.main.llBottom.ivHome)
        loadFragment(mHomeFragment)
        title = getString(R.string.domain)
        if (mHomeFragment is HomeFragment) {
            (mHomeFragment as HomeFragment)
        }
    }

}