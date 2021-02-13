package `in`.dimigo.dimigoin.ui.main.fragment.timetable

import `in`.dimigo.dimigoin.ui.item.SubjectItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimetableViewModel : ViewModel() {
    private var _timetable = MutableLiveData<List<SubjectItem?>>()
    val timetable = _timetable

    init {
        getTimetable()
    }

    fun getTimetable() {

    }
}
