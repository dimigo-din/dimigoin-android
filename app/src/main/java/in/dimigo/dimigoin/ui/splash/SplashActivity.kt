package `in`.dimigo.dimigoin.ui.splash

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.usecase.auth.AuthUseCase
import `in`.dimigo.dimigoin.data.usecase.user.UserUseCase
import `in`.dimigo.dimigoin.data.util.SharedPreferencesManager
import `in`.dimigo.dimigoin.di.restartKoin
import `in`.dimigo.dimigoin.ui.BaseActivity
import `in`.dimigo.dimigoin.ui.attendance.AttendanceActivity
import `in`.dimigo.dimigoin.ui.login.LoginActivity
import `in`.dimigo.dimigoin.ui.main.MainActivity
import `in`.dimigo.dimigoin.ui.util.startActivityTransition
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity() {
    private val userUseCase: UserUseCase by inject()
    private val authUseCase: AuthUseCase by inject()
    private val sharedPreferencesManager: SharedPreferencesManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val logoutRequested = intent.getBooleanExtra(KEY_LOGOUT, false)

        lifecycleScope.launch {
            if (logoutRequested) {
                authUseCase.logout()
                restartKoin()
                withContext(Dispatchers.Main) { taskFinished(LoginActivity::class.java) }
            } else {
                val autoLoginSucceeded = tryAutoLogin()
                val destinationActivity = if (autoLoginSucceeded) {
                    if (userData.isTeacher()) AttendanceActivity::class.java
                    else MainActivity::class.java
                } else {
                    LoginActivity::class.java
                }
                withContext(Dispatchers.Main) { taskFinished(destinationActivity) }
            }
        }
    }

    private suspend fun tryAutoLogin(): Boolean {
        try {
            if (sharedPreferencesManager.accessToken == null) return false
            userUseCase.storeUserData()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, R.string.login_failed_check_network, Toast.LENGTH_LONG).show()
            return false
        }
    }

    private fun <T : Activity> taskFinished(destinationActivity: Class<T>) {
        startActivity(Intent(this, destinationActivity))
        finish()
        startActivityTransition()
    }

    companion object {
        const val KEY_LOGOUT = "logout"
    }
}
