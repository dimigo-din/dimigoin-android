package `in`.dimigo.dimigoin.ui.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.ActivityMainBinding
import `in`.dimigo.dimigoin.ui.custom.BaseActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.forEach
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private var bottomNavBarBottomPadding = 0
    private var isFullScreen = false

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
        isFullScreen = true
    }

    fun showBottomNavBar() {
        binding.motionLayout.transitionToStart()
    }

    @Suppress("DEPRECATION")
    fun exitFullScreen() {
        enableBottomNavBar(true)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        binding.mainBottomNav.updatePadding(bottom = bottomNavBarBottomPadding)
        isFullScreen = false
    }

    override fun onBackPressed() {
        if (isFullScreen) viewModel.hideCard.value = null
        else super.onBackPressed()
    }

    private fun enableBottomNavBar(enable: Boolean) = with(binding.mainBottomNav) {
        menu.forEach {
            it.isEnabled = enable
        }
    }

    private fun initView() {
        val fragment = supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment
        val navController = fragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            lifecycleScope.launch(Dispatchers.Main) {
                binding.schoolBackground.visibility =
                    if (destination.id == R.id.main) View.INVISIBLE
                    else View.VISIBLE
            }
        }

        with(binding.mainBottomNav) {
            setupWithNavController(navController)
            selectedItemId = R.id.main
            bottomNavBarBottomPadding = paddingBottom
        }
    }
}
