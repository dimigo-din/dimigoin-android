package `in`.dimigo.dimigoin.ui.login

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.ActivityLoginBinding
import `in`.dimigo.dimigoin.ui.main.MainActivity
import `in`.dimigo.dimigoin.ui.util.observeEvent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    private fun handleEvent(event: Event) {
        when (event) {
            Event.LoginSuccess -> loginFinished()
            Event.LoginFail -> {
                Toast.makeText(this, R.string.login_failed, Toast.LENGTH_LONG).show()
            }
        }
    }

    enum class Event {
        LoginSuccess, LoginFail
    }
}
