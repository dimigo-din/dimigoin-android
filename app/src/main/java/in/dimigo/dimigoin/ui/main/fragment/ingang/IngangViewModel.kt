package `in`.dimigo.dimigoin.ui.main.fragment.ingang

import `in`.dimigo.dimigoin.data.model.IngangTime
import `in`.dimigo.dimigoin.data.usecase.ingang.IngangUseCase
import `in`.dimigo.dimigoin.ui.item.IngangStatusItem
import `in`.dimigo.dimigoin.ui.util.EventWrapper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class IngangViewModel(private val ingangUseCase: IngangUseCase) : ViewModel() {
    private val _ingangStatus = MutableLiveData<IngangStatusItem>()
    val ingangStatus: LiveData<IngangStatusItem> = _ingangStatus

    private val _time1Requested = MutableLiveData(false)
    val time1Requested: LiveData<Boolean> = _time1Requested
    private val _time2Requested = MutableLiveData(false)
    val time2Requested: LiveData<Boolean> = _time2Requested

    private val _event = MutableLiveData<EventWrapper<IngangFragment.Event>>()
    val event: LiveData<EventWrapper<IngangFragment.Event>> = _event
    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private var ingangStatusRequested = false

    init {
        refresh(true)
    }

    fun refresh(isInitial: Boolean) = viewModelScope.launch {
        if (!isInitial) _isRefreshing.value = true
        fetchIngangStatus()
        if (!isInitial) _isRefreshing.value = false
    }

    fun onApplyButtonClick(time: IngangTime) = viewModelScope.launch {
        if (!canRequest()) return@launch
        setIngangRequested(time, true)

        if (ingangStatus.value?.isApplied(time) == true) cancelIngang(time)
        else applyIngang(time)

        setIngangRequested(time, false)
    }

    private suspend fun fetchIngangStatus() {
        ingangStatusRequested = true
        ingangUseCase.getIngangStatus().onSuccess {
            _ingangStatus.value = it
        }.onFailure {
            _event.value = EventWrapper(IngangFragment.Event.IngangStatusRequestFail)
        }
        ingangStatusRequested = false
    }

    private suspend fun applyIngang(time: IngangTime) {
        ingangUseCase.applyIngang(time).onSuccess {
            _event.value = EventWrapper(IngangFragment.Event.IngangApplied(time))
            fetchIngangStatus()
        }.onFailure {
            _event.value = EventWrapper(IngangFragment.Event.IngangApplyFail)
        }
    }

    private suspend fun cancelIngang(time: IngangTime) {
        ingangUseCase.cancelIngang(time).onSuccess {
            fetchIngangStatus()
        }.onFailure {
            _event.value = EventWrapper(IngangFragment.Event.IngangCancelFail)
        }
    }

    private fun canRequest() =
        !ingangStatusRequested && time1Requested.value == false && time2Requested.value == false

    private fun setIngangRequested(time: IngangTime, requested: Boolean) {
        if (time == IngangTime.NSS1) _time1Requested.value = requested
        else _time2Requested.value = requested
    }
}
