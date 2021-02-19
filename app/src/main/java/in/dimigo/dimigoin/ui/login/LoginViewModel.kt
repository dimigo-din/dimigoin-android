package `in`.dimigo.dimigoin.ui.login

import `in`.dimigo.dimigoin.data.model.LoginRequestModel
import `in`.dimigo.dimigoin.data.usecase.auth.AuthUseCase
import `in`.dimigo.dimigoin.data.usecase.user.UserUseCase
import `in`.dimigo.dimigoin.ui.util.EventWrapper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(private val useCase: AuthUseCase, private val userUseCase: UserUseCase) : ViewModel() {
    private val _event = MutableLiveData<EventWrapper<LoginActivity.Event>>()
    val event: LiveData<EventWrapper<LoginActivity.Event>> = _event
    private val _isLoginRequested = MutableLiveData<Boolean>()
    val isLoginRequested: LiveData<Boolean> = _isLoginRequested

    val id = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val idErrorEnabled = MutableLiveData(false)
    val pwErrorEnabled = MutableLiveData(false)

    fun onLoginButtonClick() {
        _event.value = EventWrapper(LoginActivity.Event.LoginButtonClicked)
        if (!checkValidation()) return

        val loginRequestModel = LoginRequestModel(id.value ?: "", password.value ?: "")
        viewModelScope.launch {
            _isLoginRequested.value = true
            login(loginRequestModel)
        }
    }

    private fun checkValidation(): Boolean {
        var isValid = true
        if (id.value.isNullOrBlank()) {
            idErrorEnabled.value = true
            isValid = false
        }
        if (password.value.isNullOrBlank()) {
            pwErrorEnabled.value = true
            isValid = false
        }
        return isValid
    }

    private suspend fun login(loginRequestModel: LoginRequestModel) {
        val loginSucceeded = useCase.tryLogin(loginRequestModel)
        if (!loginSucceeded) {
            _event.value = EventWrapper(LoginActivity.Event.LoginFail)
            _isLoginRequested.value = false
            return
        }

        try {
            userUseCase.storeUserData()
            _event.value = EventWrapper(LoginActivity.Event.LoginSuccess)
        } catch (e: Exception) {
            e.printStackTrace()
            _isLoginRequested.value = false
            _event.value = EventWrapper(LoginActivity.Event.LoginFail)
        }
    }
}
