package `in`.dimigo.dimigoin.ui.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.util.AccessToken
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.ActivityMainBinding
import `in`.dimigo.dimigoin.ui.login.LoginActivity.Companion.KEY_TOKEN
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val sharedPreferences: SharedPreferences by inject()

    init {
        storeUserData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView(binding)
    }

    private fun storeUserData() {
        val token = sharedPreferences.getString(KEY_TOKEN, null) ?: return
        val userModel = AccessToken(token).userModel
        UserDataStore.userData = userModel
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
