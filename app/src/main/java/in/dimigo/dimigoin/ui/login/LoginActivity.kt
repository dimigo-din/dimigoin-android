package `in`.dimigo.dimigoin.ui.login

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.AuthModel
import `in`.dimigo.dimigoin.databinding.ActivityLoginBinding
import `in`.dimigo.dimigoin.ui.util.observeEvent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_App)
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login).apply {
            lifecycleOwner = this@LoginActivity
            vm = viewModel
        }

        viewModel.event.observeEvent(this, {
            handleEvent(it)
        })
    }

    private fun handleEvent(event: Event) = when (event) {
        is Event.LoginSuccess -> {
            // TODO : Auto Login, Start Activity
        }
        is Event.LoginFail -> {
            Toast.makeText(this, R.string.login_failed, Toast.LENGTH_LONG).show()
        }
    }

    sealed class Event {
        data class LoginSuccess(val authModel: AuthModel) : Event()
        object LoginFail : Event()
    }
}
