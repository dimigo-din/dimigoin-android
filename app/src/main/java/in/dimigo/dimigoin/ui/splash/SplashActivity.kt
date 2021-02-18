package `in`.dimigo.dimigoin.ui.splash

import `in`.dimigo.dimigoin.data.usecase.user.UserUseCase
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.ui.attendance.AttendanceActivity
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
        UserDataStore.userData = userUseCase.getMyInfo()
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    private fun loginFinished(isAutoLoginSuccess: Boolean) {
        startActivity(
            if (isAutoLoginSuccess) {
                if (UserDataStore.userData.userType == LoginActivity.TYPE_TEACHER)
                    Intent(this, AttendanceActivity::class.java)
                else
                    Intent(this, MainActivity::class.java)
            } else Intent(this@SplashActivity, LoginActivity::class.java)
        )
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
