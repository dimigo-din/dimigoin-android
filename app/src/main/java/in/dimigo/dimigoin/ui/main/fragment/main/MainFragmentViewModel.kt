package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.PlaceModel
import `in`.dimigo.dimigoin.data.model.PrimaryPlaceModel
import `in`.dimigo.dimigoin.data.usecase.attendance.AttendanceUseCase
import `in`.dimigo.dimigoin.data.usecase.config.ConfigUseCase
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import `in`.dimigo.dimigoin.data.usecase.notice.NoticeUseCase
import `in`.dimigo.dimigoin.ui.item.MealItem
import `in`.dimigo.dimigoin.ui.item.NoticeItem
import `in`.dimigo.dimigoin.ui.util.EventWrapper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainFragmentViewModel(
    private val mealUseCase: MealUseCase,
    private val attendanceUseCase: AttendanceUseCase,
    private val noticeUseCase: NoticeUseCase,
    private val configUseCase: ConfigUseCase
) : ViewModel() {
    private val _attendanceLocation = MutableLiveData(AttendanceLocation.Class)
    val attendanceLocation: LiveData<AttendanceLocation> = _attendanceLocation
    private val _todayMeal = MutableLiveData<MealItem>()
    val todayMeal: LiveData<MealItem> = _todayMeal
    private val _notice = MutableLiveData<NoticeItem>()
    val notice: LiveData<NoticeItem> = _notice
    private val _currentTimeCode = MutableLiveData("")
    val currentTimeCode: LiveData<String> = _currentTimeCode
    private val _event = MutableLiveData<EventWrapper<MainFragment.Event>>()
    val event: LiveData<EventWrapper<MainFragment.Event>> = _event
    private val _attendanceRequestingCount = MutableLiveData(0)
    val attendanceRequestingCount: LiveData<Int> = _attendanceRequestingCount

    var places: List<PlaceModel>? = null
    private var primaryPlaces: List<PrimaryPlaceModel>? = null
    private var previousAttendanceLocation: AttendanceLocation? = null

    init {
        updateCurrentLocation()
        updateNotice()
        updateTodayMeal()
        updateCurrentTimeCode()
        fetchPlaces()
    }

    fun onAttendanceLocationButtonClicked(location: AttendanceLocation) {
        if (location == AttendanceLocation.Etc) {
            _event.value = EventWrapper(MainFragment.Event.LocationEtcClicked)
            return
        }
        previousAttendanceLocation = attendanceLocation.value
        _attendanceLocation.value = location

        viewModelScope.launch {
            _attendanceRequestingCount.increase()

            if (primaryPlaces == null) fetchPrimaryPlaces()
            val place = location.getPrimaryPlace(primaryPlaces)
            changeCurrentAttendancePlace(place)

            _attendanceRequestingCount.decrease()
        }
    }

    private suspend fun changeCurrentAttendancePlace(place: PrimaryPlaceModel?) {
        _attendanceRequestingCount.increase()
        try {
            attendanceUseCase.changeCurrentAttendancePlace(place ?: throw PlaceNullException())
            updateCurrentLocation()
        } catch (e: PlaceNullException) {
            e.printStackTrace()
            _event.value = EventWrapper(MainFragment.Event.Error(R.string.not_supported_place))
            _attendanceLocation.value = previousAttendanceLocation
        } catch (e: Exception) {
            e.printStackTrace()
            _event.value = EventWrapper(MainFragment.Event.Error(R.string.failed_to_change_location))
            _attendanceLocation.value = previousAttendanceLocation
        }
        _attendanceRequestingCount.decrease()
    }

    fun changeCurrentAttendancePlace(place: PlaceModel, remark: String) = viewModelScope.launch {
        _attendanceRequestingCount.increase()
        try {
            attendanceUseCase.changeCurrentAttendancePlace(place, remark)
            updateCurrentLocation()
        } catch (e: Exception) {
            e.printStackTrace()
            _event.value = EventWrapper(MainFragment.Event.Error(R.string.failed_to_change_location))
            _attendanceLocation.value = previousAttendanceLocation
        }
        _attendanceRequestingCount.decrease()
    }

    private fun updateCurrentLocation() = viewModelScope.launch {
        _attendanceRequestingCount.increase()
        if (primaryPlaces == null) fetchPrimaryPlaces()
        try {
            val primaryPlaces = primaryPlaces ?: throw Exception("Variable primaryPlaces is null")
            val currentPlace = attendanceUseCase.getCurrentAttendancePlace()
                .toPrimaryPlaceModel(primaryPlaces)
            val location = AttendanceLocation.fromPrimaryPlace(currentPlace)
            _attendanceLocation.value = location
        } catch (e: NoSuchElementException) {
            e.printStackTrace()
            _attendanceLocation.value = AttendanceLocation.Class
        } catch (e: Exception) {
            e.printStackTrace()
            _event.value = EventWrapper(MainFragment.Event.Error(R.string.failed_to_fetch_current_location))
        }
        _attendanceRequestingCount.decrease()
    }

    private suspend fun fetchPrimaryPlaces() {
        _attendanceRequestingCount.increase()
        try {
            primaryPlaces = attendanceUseCase.getPrimaryPlaces()
        } catch (e: Exception) {
            e.printStackTrace()
            _event.value = EventWrapper(MainFragment.Event.Error(R.string.failed_to_fetch_places))
        }
        _attendanceRequestingCount.decrease()
    }

    fun fetchPlaces() = viewModelScope.launch {
        _attendanceRequestingCount.increase()
        try {
            places = attendanceUseCase.getAllPlaces().sortedBy { it.type }
        } catch (e: Exception) {
            e.printStackTrace()
            _event.value = EventWrapper(MainFragment.Event.Error(R.string.failed_to_fetch_places))
        }
        _attendanceRequestingCount.decrease()
    }

    private fun updateNotice() = viewModelScope.launch {
        try {
            _notice.value = noticeUseCase.getNotice()
        } catch (e: Exception) {
            e.printStackTrace()
            _notice.value = noticeUseCase.failedNotice
        }
    }

    private fun updateTodayMeal() = viewModelScope.launch {
        try {
            _todayMeal.value = mealUseCase.getTodaysMeal()
        } catch (e: Exception) {
            e.printStackTrace()
            _todayMeal.value = mealUseCase.failedMeal
        }
    }

    private fun updateCurrentTimeCode() = viewModelScope.launch {
        try {
            _currentTimeCode.value = configUseCase.getCurrentTimeCode()
        } catch (e: Exception) {
            e.printStackTrace()
            _currentTimeCode.value = ""
        }
    }
}

private fun MutableLiveData<Int>.increase() {
    value = (value ?: 0) + 1
}

private fun MutableLiveData<Int>.decrease() {
    value = (value ?: 0) - 1
}

private class PlaceNullException : Exception("Place is null")
