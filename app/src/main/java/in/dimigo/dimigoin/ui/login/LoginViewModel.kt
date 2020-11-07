package `in`.dimigo.dimigoin.ui.login

import `in`.dimigo.dimigoin.data.model.LoginRequestModel
import `in`.dimigo.dimigoin.data.usecase.AuthUseCase
import `in`.dimigo.dimigoin.ui.util.EventWrapper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(private val useCase: AuthUseCase) : ViewModel() {
    private val _event = MutableLiveData<EventWrapper<LoginActivity.Event>>()

    val id = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val event: LiveData<EventWrapper<LoginActivity.Event>> = _event

    fun onLoginButtonClick() {
        val loginRequestModel = LoginRequestModel(id.value ?: "", password.value ?: "")
        viewModelScope.launch { login(loginRequestModel) }
    }

    private suspend fun login(loginRequestModel: LoginRequestModel) {
        try {
            val authModel = useCase.login(loginRequestModel)
            _event.value = EventWrapper(LoginActivity.Event.LoginSuccess(authModel))
        } catch (e: Exception) {
            e.printStackTrace()
            _event.value = EventWrapper(LoginActivity.Event.LoginFail)
        }
    }
}
