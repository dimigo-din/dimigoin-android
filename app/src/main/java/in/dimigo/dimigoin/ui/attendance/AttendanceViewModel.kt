package `in`.dimigo.dimigoin.ui.attendance

import `in`.dimigo.dimigoin.data.model.*
import `in`.dimigo.dimigoin.data.usecase.attendance.AttendanceUseCase
import `in`.dimigo.dimigoin.data.usecase.config.ConfigUseCase
import `in`.dimigo.dimigoin.data.util.DateUtil
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.ui.custom.PlaceProvider
import `in`.dimigo.dimigoin.ui.item.AttendanceDetailItem
import `in`.dimigo.dimigoin.ui.item.AttendanceItem
import `in`.dimigo.dimigoin.ui.main.fragment.main.AttendanceLocation
import `in`.dimigo.dimigoin.ui.util.EventWrapper
import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class AttendanceViewModel(
    private val attendanceUseCase: AttendanceUseCase,
    private val configUseCase: ConfigUseCase,
    userDataStore: UserDataStore
) : ViewModel(), PlaceProvider {
    private val userData = userDataStore.requireUserData()
    val isTeacher = userData.isTeacher()
    val hasAttendancePermission = userData.hasPermission(Permission.ATTENDANCE)

    private val _attendanceTableData = MutableLiveData<List<Int>>()
    val attendanceTableData: LiveData<List<Int>> = _attendanceTableData

    private val _attendanceData = MutableLiveData<List<AttendanceItem>>()
    val attendanceData: LiveData<List<AttendanceItem>> = _attendanceData

    private val _attendanceLogs = MutableLiveData<List<AttendanceLogModel>?>()
    val attendanceLogs: LiveData<List<AttendanceLogModel>?> = _attendanceLogs

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _event = MutableLiveData<EventWrapper<AttendanceFragment.Event>>()
    val event: LiveData<EventWrapper<AttendanceFragment.Event>> = _event

    private val _currentTimeCode = MutableLiveData("")
    val currentTimeCode: LiveData<String> = _currentTimeCode

    val grade = MutableLiveData(1)
    val klass = MutableLiveData(1)

    val query = MutableLiveData<String>()

    override var places: List<PlaceModel>? = null

    init {
        refresh()
    }

    fun refresh() = viewModelScope.launch {
        _isRefreshing.value = true
        if (!isTeacher) {
            grade.value = userData.grade
            klass.value = userData.klass
        }

        if (isTeacher || hasAttendancePermission) awaitAll(
            async { fetchAttendanceStatus() },
            async { updateCurrentTimeCode() },
            async { fetchAttendanceTimeline() },
            async { fetchPlaces() }
        ) else awaitAll(
            async { fetchAttendanceStatus() },
            async { updateCurrentTimeCode() }
        )

        _isRefreshing.value = false
    }

    fun onHistoryButtonClick() {
        if (!isTeacher && !hasAttendancePermission) return
        _event.value = EventWrapper(AttendanceFragment.Event.ShowHistoryDialog)
    }

    fun onPlaceButtonClick(item: AttendanceItem) {
        if (!isTeacher && !hasAttendancePermission) return
        _event.value = EventWrapper(AttendanceFragment.Event.ShowSelectPlaceDialog(item))
    }

    fun onAttendanceDetailButtonClick(item: AttendanceItem) {
        if (!isTeacher && !hasAttendancePermission) return
        _event.value = EventWrapper(AttendanceFragment.Event.ShowAttendanceDetailDialog(item))
    }

    suspend fun fetchAttendanceDetail(userModel: UserModel): AttendanceDetailItem? {
        var result: AttendanceDetailItem? = null
        attendanceUseCase.getAttendanceDetail(userModel).onSuccess {
            result = it
        }
        return result
    }

    private suspend fun fetchAttendanceStatus() {
        var data: List<AttendanceStatusModel> = listOf()
        attendanceUseCase.getAttendanceStatus(grade.value ?: 1, klass.value ?: 1).onSuccess {
            data = it
        }.onFailure {
            _event.value = EventWrapper(AttendanceFragment.Event.AttendanceFetchFailed)
        }
        applyAttendanceStatus(data)
        applyAttendanceTableData(data)
    }

    @SuppressLint("NullSafeMutableLiveData")
    private suspend fun fetchAttendanceTimeline() {
        attendanceUseCase.getAttendanceTimeline(grade.value ?: 1, klass.value ?: 1).onSuccess {
            _attendanceLogs.value = it
        }.onFailure {
            _attendanceLogs.value = null
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
                result[0]++
            }
        }
        result[4] = dataList.size

        _attendanceTableData.value = result
    }

    private suspend fun updateCurrentTimeCode() {
        try {
            _currentTimeCode.value = configUseCase.getCurrentTimeCode()
        } catch (e: Exception) {
            e.printStackTrace()
            _currentTimeCode.value = ""
        }
    }

    override suspend fun fetchPlaces() {
        attendanceUseCase.getAllPlaces().onSuccess {
            places = it
        }
    }

    fun changeCurrentAttendancePlace(place: PlaceModel, remark: String, student: UserModel) {
        viewModelScope.launch {
            _isRefreshing.value = true
            attendanceUseCase.changeCurrentAttendancePlace(place, remark, student).onSuccess {
                refresh()
            }.onFailure {
                _event.value = EventWrapper(AttendanceFragment.Event.ChangeLocationFailed(student))
            }
            _isRefreshing.value = false
        }
    }
}
