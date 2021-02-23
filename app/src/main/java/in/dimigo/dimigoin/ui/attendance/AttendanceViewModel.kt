package `in`.dimigo.dimigoin.ui.attendance

import `in`.dimigo.dimigoin.data.model.AttendanceLogModel
import `in`.dimigo.dimigoin.data.model.AttendanceStatusModel
import `in`.dimigo.dimigoin.data.model.PlaceType
import `in`.dimigo.dimigoin.data.usecase.attendance.AttendanceUseCase
import `in`.dimigo.dimigoin.ui.item.AttendanceItem
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AttendanceViewModel(private val useCase: AttendanceUseCase) : ViewModel() {
    private val _attendanceTableData = MutableLiveData<List<Int>>()
    val attendanceTableData: LiveData<List<Int>> = _attendanceTableData

    private val _attendanceData = MutableLiveData<List<AttendanceItem>>()
    val attendanceData: LiveData<List<AttendanceItem>> = _attendanceData

    private val _attendanceLogs = MutableLiveData<List<AttendanceLogModel>>()
    val attendanceLogs: LiveData<List<AttendanceLogModel>> = _attendanceLogs

    val query = MutableLiveData<String>()

    suspend fun loadCurrentAttendanceData() {
        try {
            val data = useCase.getCurrentAttendanceStatus()
            applyAttendanceData(data)
        } catch (e: Exception) {
            Log.e("error", "msg: ${e.message}")
        }
    }

    suspend fun loadSpecificAttendanceData(grade: Int, klass: Int) {
        try {
            val data = useCase.getSpecificAttendanceStatus(grade, klass)
            applyAttendanceData(data)
        } catch (e: Exception) {
            Log.e("error", "msg: ${e.message}")
        }
    }

    suspend fun loadCurrentAttendanceLog(grade: Int, klass: Int) {
        try {
            val data = useCase.getAttendanceTimeline(grade, klass)
            _attendanceLogs.value = data
        } catch (e: Exception) {
            Log.e("error", "msg: ${e.message}")
        }
    }

    fun applyAttendanceData(dataList: List<AttendanceStatusModel>) {
        _attendanceData.value = dataList.map {
            AttendanceItem(
                it.student.number,
                it.student.name,
                it.log?.place
            )
        }
        _attendanceTableData.value = getAttendanceTableData(dataList)
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
                    else -> break
                }
                result[cursor]++
            } else {
                //TODO: just for temporary, keep this way setting the default value
                result[0]++
            }
        }

        result[4] = dataList.size

        return result
    }
}
