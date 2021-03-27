package `in`.dimigo.dimigoin.ui.main.fragment.timetable

import `in`.dimigo.dimigoin.data.usecase.timetable.TimetableUseCase
import `in`.dimigo.dimigoin.ui.item.SubjectItem
import `in`.dimigo.dimigoin.ui.util.SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TimetableViewModel(private val timetableUseCase: TimetableUseCase) : ViewModel() {
    private val _timetable = MutableLiveData<List<SubjectItem?>>()
    val timetable: LiveData<List<SubjectItem?>> = _timetable
    private val _timetableFetchFailedEvent = SingleLiveEvent<Void>()
    val timetableFetchFailedEvent: LiveData<Void> = _timetableFetchFailedEvent
    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    init {
        refresh()
    }

    fun refresh() = viewModelScope.launch {
        _isRefreshing.value = true
        fetchTimetable()
        _isRefreshing.value = false
    }

    private suspend fun fetchTimetable() {
        timetableUseCase.getWeeklyTimetable().onSuccess {
            _timetable.value = it
        }.onFailure {
            _timetableFetchFailedEvent.call()
        }
    }
}
