package `in`.dimigo.dimigoin.ui.attendance

import `in`.dimigo.dimigoin.data.model.AttendanceStatusModel
import `in`.dimigo.dimigoin.data.model.PlaceType
import `in`.dimigo.dimigoin.data.usecase.attendance.AttendanceUseCase
import `in`.dimigo.dimigoin.ui.item.AttendanceItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AttendanceViewModel(private val useCase: AttendanceUseCase) : ViewModel() {
    private val _attendanceTableData = MutableLiveData<List<Int>>()
    val attendanceTableData: LiveData<List<Int>> = _attendanceTableData

    private val _attendanceData = MutableLiveData<List<AttendanceItem>>()
    val attendanceData: LiveData<List<AttendanceItem>> = _attendanceData

    val query = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            loadAttendanceData()
        }
    }

    private suspend fun loadAttendanceData() {
        try {
            val data = useCase.getCurrentAttendanceStatus()

            _attendanceData.value = data.map {
                AttendanceItem(
                    it.student.number,
                    it.student.name,
                    it.log?.place?.type
                )
            }
            _attendanceTableData.value = getAttendanceTableData(data)
        } catch (e: Exception) {
            // TODO 에러 처리
        }
    }

    private fun getAttendanceTableData(dataList: List<AttendanceStatusModel>): List<Int> {
        val result = mutableListOf(0, 0, 0, 0, 0)

        for (data in dataList) {
            if (data.log != null) {
                val cursor = when (data.log.place.type) {
                    PlaceType.CLASSROOM -> 0
                    PlaceType.INGANG -> 1
                    PlaceType.CIRCLE -> 2
                    PlaceType.ETC -> 3
                }
                result[cursor]++
            }
        }

        result[4] = dataList.size

        return result
    }
}
