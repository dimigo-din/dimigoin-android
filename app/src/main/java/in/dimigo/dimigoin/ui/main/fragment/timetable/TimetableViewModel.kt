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

    init {
        viewModelScope.launch {
            fetchTimetable()
        }
    }

    private suspend fun fetchTimetable() {
        try {
            _timetable.value = timetableUseCase.getWeeklyTimetable()
        } catch (e: Exception) {
            _timetableFetchFailedEvent.call()
            e.printStackTrace()
        }
    }
}
