package `in`.dimigo.dimigoin.ui.splash

import `in`.dimigo.dimigoin.data.usecase.user.UserUseCase
import `in`.dimigo.dimigoin.ui.login.LoginActivity
import `in`.dimigo.dimigoin.ui.main.MainActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {
    private val userUseCase: UserUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val autoLoginSucceeded = tryAutoLogin()
            withContext(Dispatchers.Main) { loginFinished(autoLoginSucceeded) }
        }
    }

    private suspend fun tryAutoLogin() = try {
        userUseCase.storeUserData()
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    private fun loginFinished(isAutoLoginSuccess: Boolean) {
        startActivity(
            if (isAutoLoginSuccess) Intent(this@SplashActivity, MainActivity::class.java)
            else Intent(this@SplashActivity, LoginActivity::class.java)
        )
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
