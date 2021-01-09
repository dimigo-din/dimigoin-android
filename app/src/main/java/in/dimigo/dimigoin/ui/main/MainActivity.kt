package `in`.dimigo.dimigoin.ui.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.ActivityMainBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView(binding)
    }

    private fun initView(binding: ActivityMainBinding) {
        val fragment = supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment
        val navController = fragment.navController
        binding.mainBottomNav.apply {
            setupWithNavController(navController)
            selectedItemId = R.id.main
        }
    }
}
