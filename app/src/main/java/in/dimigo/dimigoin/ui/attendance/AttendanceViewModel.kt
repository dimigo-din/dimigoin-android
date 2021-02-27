package `in`.dimigo.dimigoin.ui.attendance

import `in`.dimigo.dimigoin.data.model.*
import `in`.dimigo.dimigoin.data.usecase.attendance.AttendanceUseCase
import `in`.dimigo.dimigoin.data.util.DateUtil
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.ui.item.AttendanceDetailItem
import `in`.dimigo.dimigoin.ui.item.AttendanceItem
import `in`.dimigo.dimigoin.ui.main.fragment.main.AttendanceLocation
import `in`.dimigo.dimigoin.ui.util.SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AttendanceViewModel(private val useCase: AttendanceUseCase) : ViewModel() {
    val isTeacher = UserDataStore.userData.userType == UserType.TEACHER

    private val _attendanceTableData = MutableLiveData<List<Int>>()
    val attendanceTableData: LiveData<List<Int>> = _attendanceTableData

    private val _attendanceData = MutableLiveData<List<AttendanceItem>>()
    val attendanceData: LiveData<List<AttendanceItem>> = _attendanceData

    private val _attendanceLogs = MutableLiveData<List<AttendanceLogModel>>()
    val attendanceLogs: LiveData<List<AttendanceLogModel>> = _attendanceLogs

    private val _attendanceDetail = MutableLiveData<AttendanceDetailItem>()
    val attendanceDetail: LiveData<AttendanceDetailItem> = _attendanceDetail

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _detailClickedEvent = SingleLiveEvent<Void>()
    val detailClickedEvent: LiveData<Void> = _detailClickedEvent

    private val _attendanceFetchFailedEvent = SingleLiveEvent<Void>()
    val attendanceFetchFailedEvent: LiveData<Void> = _attendanceFetchFailedEvent

    val grade = MutableLiveData(1)
    val klass = MutableLiveData(1)

    val query = MutableLiveData<String>()

    init {
        refresh(true)
    }

    fun refresh(isInitial: Boolean = false) = viewModelScope.launch {
        if (!isInitial) _isRefreshing.value = true

        if (isTeacher) {
            fetchSelectedAttendanceStatus()
            fetchSelectedAttendanceTimeline()
        } else {
            fetchCurrentAttendanceStatus()
        }

        if (!isInitial) _isRefreshing.value = false
    }

    fun fetchAttendanceDetail(item: AttendanceItem) {
        viewModelScope.launch {
            loadAttendanceDetail(item.student)
            _detailClickedEvent.call()
        }
    }

    //학생용, 본인 반
    private suspend fun fetchCurrentAttendanceStatus() {
        try {
            val data = useCase.getCurrentAttendanceStatus()
            applyAttendanceStatus(data)
            applyAttendanceTableData(data)
        } catch (e: Exception) {
            _attendanceFetchFailedEvent.call()
            e.printStackTrace()
        }
    }

    //교사용, 선택된 반
    private suspend fun fetchSelectedAttendanceStatus() {
        try {
            val data = useCase.getSpecificAttendanceStatus(grade.value ?: 1, klass.value ?: 1)
            applyAttendanceStatus(data)
            applyAttendanceTableData(data)
        } catch (e: Exception) {
            _attendanceFetchFailedEvent.call()
            e.printStackTrace()
        }
    }

    //교사용, 선택된 반 히스토리 조회
    private suspend fun fetchSelectedAttendanceTimeline() {
        try {
            val data = useCase.getAttendanceTimeline(grade.value ?: 1, klass.value ?: 1)
            _attendanceLogs.value = data
        } catch (e: Exception) {
            _attendanceFetchFailedEvent.call()
            e.printStackTrace()
        }
    }

    private suspend fun loadAttendanceDetail(userModel: UserModel) {
        try {
            val data = useCase.getAttendanceDetail(userModel)
            _attendanceDetail.value = data
        } catch (e: Exception) {
            _attendanceFetchFailedEvent.call()
            e.printStackTrace()
        }
    }

    private fun applyAttendanceStatus(dataList: List<AttendanceStatusModel>) {
        _attendanceData.value = dataList.map {
            val place = it.log?.place
            val location: AttendanceLocation =
                place?.let { log -> AttendanceLocation.fromPlace(log) } ?: AttendanceLocation.Class

            AttendanceItem(
                it.student,
                location,
                place?.name,
                it.log?.time?.let { time ->
                    DateUtil.toLocalDateTimeWithDefaultZone(time)
                }
            )
        }
    }

    private fun applyAttendanceTableData(dataList: List<AttendanceStatusModel>) {
        val result = mutableListOf(0, 0, 0, 0, 0)

        for (data in dataList) {
            if (data.log != null) {
                val cursor = when (data.log.place.type) {
                    PlaceType.CLASSROOM -> 0
                    PlaceType.INGANG -> 1
                    PlaceType.CIRCLE -> 2
                    PlaceType.ETC -> 3
                    PlaceType.ABSENT -> break
                }
                result[cursor]++
            } else {
                //TODO: just for temporary, keep this way setting the default value
                result[0]++
            }
        }
        result[4] = dataList.size

        _attendanceTableData.value = result
    }
}
