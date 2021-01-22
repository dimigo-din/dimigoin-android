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
        _timetable.value = makeDummyData()
    }

    private fun makeDummyData(): List<SubjectItem> {
        val list = mutableListOf<SubjectItem>()

        for (i in 1..35) {
            list.add(SubjectItem("과목", "교사명"))
        }

        return list
    }
}
