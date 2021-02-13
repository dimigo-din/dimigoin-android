package `in`.dimigo.dimigoin.ui.main.fragment.timetable

import `in`.dimigo.dimigoin.data.usecase.timetable.TimetableUseCase
import `in`.dimigo.dimigoin.ui.item.SubjectItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TimetableViewModel(private val timetableUseCase: TimetableUseCase) : ViewModel() {
    private var _timetable = MutableLiveData<List<SubjectItem?>>()
    val timetable: LiveData<List<SubjectItem?>> = _timetable

    init {
        viewModelScope.launch {
            getTimetable()
        }
    }

    suspend fun getTimetable() {
        _timetable.value = timetableUseCase.getWeeklyTimetable()
    }
}
