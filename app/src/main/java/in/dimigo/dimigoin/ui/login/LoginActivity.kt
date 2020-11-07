package `in`.dimigo.dimigoin.ui.login

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.AuthModel
import `in`.dimigo.dimigoin.databinding.ActivityLoginBinding
import `in`.dimigo.dimigoin.ui.main.MainActivity
import `in`.dimigo.dimigoin.ui.util.observeEvent
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModel()
    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_App)
        super.onCreate(savedInstanceState)

        if (tryAutoLogin()) loginFinished()

        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login).apply {
            lifecycleOwner = this@LoginActivity
            vm = viewModel
        }

        viewModel.event.observeEvent(this, {
            handleEvent(it)
        })
    }

    private fun loginFinished() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun tryAutoLogin(): Boolean {
        return sharedPreferences.getString(KEY_TOKEN, null) != null
    }

    private fun handleEvent(event: Event) = when (event) {
        is Event.LoginSuccess -> {
            sharedPreferences.edit {
                putString(KEY_TOKEN, event.authModel.token)
                putString(KEY_REFRESH_TOKEN, event.authModel.refreshToken)
            }
            loginFinished()
        }
        is Event.LoginFail -> {
            Toast.makeText(this, R.string.login_failed, Toast.LENGTH_LONG).show()
        }
    }

    sealed class Event {
        data class LoginSuccess(val authModel: AuthModel) : Event()
        object LoginFail : Event()
    }

    companion object {
        private const val KEY_TOKEN = "id"
        private const val KEY_REFRESH_TOKEN = "password"
    }
}
