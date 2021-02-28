package `in`.dimigo.dimigoin.ui.login

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.UserType
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.ActivityLoginBinding
import `in`.dimigo.dimigoin.databinding.DialogForgotIdPwBinding
import `in`.dimigo.dimigoin.ui.BaseActivity
import `in`.dimigo.dimigoin.ui.attendance.AttendanceActivity
import `in`.dimigo.dimigoin.ui.custom.DimigoinDialog
import `in`.dimigo.dimigoin.ui.main.MainActivity
import `in`.dimigo.dimigoin.ui.util.observeEvent
import `in`.dimigo.dimigoin.ui.util.startActivityTransition
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {
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
            DimigoinDialog(this@LoginActivity).CustomView(dialogView).show()
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
                DimigoinDialog(this).alert(DimigoinDialog.AlertType.ERROR, R.string.login_failed)
            }
        }
    }

    private fun loginFinished() {
        val destinationClass =
            if (UserDataStore.userData.userType == UserType.TEACHER) AttendanceActivity::class.java
            else MainActivity::class.java
        startActivity(Intent(this, destinationClass))
        finish()
        showWelcomeToast()
        startActivityTransition()
    }

    private fun showWelcomeToast() {
        val welcomeStringId =
            if (UserDataStore.userData.userType == UserType.TEACHER) R.string.welcome_teacher
            else R.string.welcome_student
        val welcomeString = getString(welcomeStringId, UserDataStore.userData.name)
        Toast.makeText(this, welcomeString, Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboard() {
        val inputMethodManager = ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    enum class Event {
        LoginButtonClicked, LoginSuccess, LoginFail
    }
}
