package `in`.dimigo.dimigoin.ui.login

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.ActivityLoginBinding
import `in`.dimigo.dimigoin.databinding.DialogForgotIdPwBinding
import `in`.dimigo.dimigoin.ui.custom.DimigoinDialog
import `in`.dimigo.dimigoin.ui.main.MainActivity
import `in`.dimigo.dimigoin.ui.util.observeEvent
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login).apply {
            lifecycleOwner = this@LoginActivity
            vm = viewModel
        }

        initView()

        viewModel.event.observeEvent(this, {
            handleEvent(it)
        })
    }

    private fun initView() = with(binding) {
        idEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) viewModel.idErrorEnabled.value = false
        }
        pwEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) viewModel.pwErrorEnabled.value = false
        }
        findIdPwText.setOnClickListener {
            val dialogView = DialogForgotIdPwBinding.inflate(layoutInflater).root
            DimigoinDialog(this@LoginActivity).CustomView(dialogView).apply {
                usePositiveButton(R.string.ok) {
                    it.dismiss()
                }
            }.show()
        }
    }

    private fun handleEvent(event: Event) {
        when (event) {
            Event.LoginButtonClicked -> {
                hideKeyboard()
                binding.idEditText.clearFocus()
                binding.pwEditText.clearFocus()
            }
            Event.LoginSuccess -> loginFinished()
            Event.LoginFail -> {
                Toast.makeText(this, R.string.login_failed, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loginFinished() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun hideKeyboard() {
        val inputMethodManager = ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    enum class Event {
        LoginButtonClicked, LoginSuccess, LoginFail
    }
}
