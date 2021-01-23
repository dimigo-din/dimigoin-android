package `in`.dimigo.dimigoin.ui.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var bottomNavBarBottomPadding = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
    }

    @Suppress("DEPRECATION")
    fun enterFullScreen() {
        enableBottomNavBar(false)
        binding.motionLayout.transitionToEnd()
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    fun showBottomNavBar() {
        binding.motionLayout.transitionToStart()
    }

    @Suppress("DEPRECATION")
    fun exitFullScreen() {
        enableBottomNavBar(true)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        binding.mainBottomNav.updatePadding(bottom = bottomNavBarBottomPadding)
    }

    private fun enableBottomNavBar(enable: Boolean) = with(binding.mainBottomNav) {
        menu.forEach {
            it.isEnabled = enable
        }
    }

    private fun initView() {
        val fragment = supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment
        val navController = fragment.navController
        with(binding.mainBottomNav) {
            setupWithNavController(navController)
            selectedItemId = R.id.main
            bottomNavBarBottomPadding = paddingBottom
        }
    }
}
