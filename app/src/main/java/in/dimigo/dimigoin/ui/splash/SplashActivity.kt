package `in`.dimigo.dimigoin.ui.splash

import `in`.dimigo.dimigoin.data.usecase.auth.AuthUseCase
import `in`.dimigo.dimigoin.data.usecase.user.UserUseCase
import `in`.dimigo.dimigoin.data.util.UserDataStore
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
    private val authUseCase: AuthUseCase by inject()
    private val userUseCase: UserUseCase by inject()
    private var autoLoginTryCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val autoLoginSucceeded = tryAutoLogin()
            withContext(Dispatchers.Main) { loginFinished(autoLoginSucceeded) }
        }
    }

    private fun loginFinished(isAutoLoginSuccess: Boolean) {
        startActivity(
            if (isAutoLoginSuccess) Intent(this@SplashActivity, MainActivity::class.java)
            else Intent(this@SplashActivity, LoginActivity::class.java)
        )
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private suspend fun tryAutoLogin(): Boolean {
        if (autoLoginTryCount >= 2) return false
        try {
            autoLoginTryCount++
            UserDataStore.userData = userUseCase.getMyInfo()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            if (authUseCase.tryTokenRefresh()) return tryAutoLogin()
            return false
        }
    }
}
