package `in`.dimigo.dimigoin.ui.splash

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.usecase.auth.AuthUseCase
import `in`.dimigo.dimigoin.ui.login.LoginActivity
import `in`.dimigo.dimigoin.ui.main.MainActivity
import `in`.dimigo.dimigoin.ui.splash.util.AccessToken
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {
    private val sharedPreferences: SharedPreferences by inject()
    private val authUseCase: AuthUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val isAutoLoginSuccess = tryAutoLogin()
            withContext(Dispatchers.Main) { loginFinished(isAutoLoginSuccess) }
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
        val tokenString = sharedPreferences.getString(LoginActivity.KEY_TOKEN, null) ?: return false
        if (AccessToken(tokenString).isTokenExpired()) {
            val refreshToken = sharedPreferences.getString(LoginActivity.KEY_REFRESH_TOKEN, null) ?: return false
            return refreshToken(refreshToken)
        }
        return true
    }

    private suspend fun refreshToken(refreshToken: String): Boolean {
        return try {
            val authModel = authUseCase.refreshToken(refreshToken)
            sharedPreferences.edit {
                putString(LoginActivity.KEY_TOKEN, authModel.token)
                putString(LoginActivity.KEY_REFRESH_TOKEN, authModel.refreshToken)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, R.string.token_expired, Toast.LENGTH_LONG).show()
            false
        }
    }
}
