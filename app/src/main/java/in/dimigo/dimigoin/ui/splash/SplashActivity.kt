package `in`.dimigo.dimigoin.ui.splash

import `in`.dimigo.dimigoin.data.usecase.auth.AuthUseCase
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
    private val authUseCase: AuthUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val logoutRequested = intent.getBooleanExtra(KEY_LOGOUT, false)

        lifecycleScope.launch {
            if (logoutRequested) {
                authUseCase.logout()
                withContext(Dispatchers.Main) { taskFinished(false) }
            } else {
                val autoLoginSucceeded = tryAutoLogin()
                withContext(Dispatchers.Main) { taskFinished(autoLoginSucceeded) }
            }
        }
    }

    private suspend fun tryAutoLogin() = try {
        userUseCase.storeUserData()
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    private fun taskFinished(goToMainActivity: Boolean) {
        startActivity(
            if (goToMainActivity) Intent(this@SplashActivity, MainActivity::class.java)
            else Intent(this@SplashActivity, LoginActivity::class.java)
        )
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    companion object {
        const val KEY_LOGOUT = "logout"
    }
}
