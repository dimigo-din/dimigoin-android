package `in`.dimigo.dimigoin.ui.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
    }

    @Suppress("DEPRECATION")
    fun enterFullScreen() {
        binding.motionLayout.transitionToEnd()
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    @Suppress("DEPRECATION")
    fun exitFullScreen() {
        binding.mainBottomNav.visibility = View.VISIBLE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    fun showBottomNavBar() {
        binding.motionLayout.transitionToStart()
    }

    private fun initView() {
        val fragment = supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment
        val navController = fragment.navController
        binding.mainBottomNav.apply {
            setupWithNavController(navController)
            selectedItemId = R.id.main
        }
    }
}
