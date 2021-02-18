package `in`.dimigo.dimigoin.ui.attendance

import `in`.dimigo.dimigoin.data.usecase.attendance.AttendanceUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AttendanceViewModel(private val useCase: AttendanceUseCase) : ViewModel() {
    private val _attendanceTableData = MutableLiveData<List<Int?>>()
    val attendanceTableData: LiveData<List<Int?>> = _attendanceTableData

    init {
        viewModelScope.launch {
            _attendanceTableData.value = getAttendanceData()
        }
    }

    private fun getAttendanceData(): List<Int> {
        return listOf(21, 34, 54, 2, 23)
    }
}
