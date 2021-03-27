package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.AttendanceLogModel
import `in`.dimigo.dimigoin.data.model.PlaceModel
import `in`.dimigo.dimigoin.data.model.PrimaryPlaceModel
import `in`.dimigo.dimigoin.data.usecase.attendance.AttendanceUseCase
import `in`.dimigo.dimigoin.data.usecase.attendance.PlaceNullException
import `in`.dimigo.dimigoin.data.usecase.config.ConfigUseCase
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import `in`.dimigo.dimigoin.data.usecase.notice.NoticeUseCase
import `in`.dimigo.dimigoin.ui.custom.PlaceProvider
import `in`.dimigo.dimigoin.ui.item.MealItem
import `in`.dimigo.dimigoin.ui.item.NoticeItem
import `in`.dimigo.dimigoin.ui.util.EventWrapper
import androidx.lifecycle.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class MainFragmentViewModel(
    private val mealUseCase: MealUseCase,
    private val attendanceUseCase: AttendanceUseCase,
    private val noticeUseCase: NoticeUseCase,
    private val configUseCase: ConfigUseCase
) : ViewModel(), PlaceProvider {
    private val _attendanceLocation = MutableLiveData(AttendanceLocation.Class)
    val attendanceLocation: LiveData<AttendanceLocation> = _attendanceLocation
    private val _todayMeal = MutableLiveData<MealItem>()
    val todayMeal: LiveData<MealItem> = _todayMeal
    private val _noticeList = MutableLiveData<List<NoticeItem>>()
    val noticeList: LiveData<List<NoticeItem>> = _noticeList
    private val _currentTimeCode = MutableLiveData("")
    val currentTimeCode: LiveData<String> = _currentTimeCode
    private val _event = MutableLiveData<EventWrapper<MainFragment.Event>>()
    val event: LiveData<EventWrapper<MainFragment.Event>> = _event
    private val _attendanceRequestingCount = MutableLiveData(0)
    val isAttendanceRequesting = _attendanceRequestingCount.map { it > 0 }
    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    var currentAttendanceLog: AttendanceLogModel? = null
    private var primaryPlaces: List<PrimaryPlaceModel>? = null
    private var previousAttendanceLocation: AttendanceLocation? = null
    override var places: List<PlaceModel>? = null

    init {
        refresh(true)
    }

    fun refresh(isInitial: Boolean) = viewModelScope.launch {
        if (!isInitial) _isRefreshing.value = true
        awaitAll(
            async { updateCurrentLocation() },
            async { updateNotice() },
            async { updateTodayMeal() },
            async { updateCurrentTimeCode() },
            async { fetchPlaces() }
        )
        if (!isInitial) _isRefreshing.value = false
    }

    fun onAttendanceLocationButtonClicked(location: AttendanceLocation) {
        if (isAttendanceRequesting.value == true) return
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
        attendanceUseCase.changeCurrentAttendancePlace(place).onSuccess { changedPlace ->
            _event.value = EventWrapper(MainFragment.Event.AttendanceLocationChanged(changedPlace.name))
            updateCurrentLocation()
        }.onFailure {
            if (it is PlaceNullException)
                _event.value = EventWrapper(MainFragment.Event.Error(R.string.not_supported_place))
            else _event.value = EventWrapper(MainFragment.Event.Error(R.string.failed_to_change_location))
            _attendanceLocation.value = previousAttendanceLocation
        }
        _attendanceRequestingCount.decrease()
    }

    fun changeCurrentAttendancePlace(place: PlaceModel, remark: String) {
        viewModelScope.launch {
            _attendanceRequestingCount.increase()
            attendanceUseCase.changeCurrentAttendancePlace(place, remark).onSuccess {
                _event.value = EventWrapper(MainFragment.Event.AttendanceLocationChanged(place.name))
                updateCurrentLocation()
            }.onFailure {
                _event.value = EventWrapper(MainFragment.Event.Error(R.string.failed_to_change_location))
                _attendanceLocation.value = previousAttendanceLocation
            }
            _attendanceRequestingCount.decrease()
        }
    }

    private suspend fun updateCurrentLocation() {
        _attendanceRequestingCount.increase()
        if (primaryPlaces == null) fetchPrimaryPlaces()

        attendanceUseCase.getMyCurrentAttendanceLog().onSuccess { currentAttendanceLog ->
            this.currentAttendanceLog = currentAttendanceLog
            val currentPlace = currentAttendanceLog.place.toPrimaryPlaceModel(requireNotNull(primaryPlaces))
            val location = AttendanceLocation.fromPrimaryPlace(currentPlace)
            _attendanceLocation.value = location
        }.onFailure { throwable ->
            if (throwable is NoSuchElementException) _attendanceLocation.value = AttendanceLocation.Class
            else _event.value = EventWrapper(MainFragment.Event.Error(R.string.failed_to_fetch_current_location))
        }
        _attendanceRequestingCount.decrease()
    }

    private suspend fun fetchPrimaryPlaces() {
        _attendanceRequestingCount.increase()
        attendanceUseCase.getPrimaryPlaces().onSuccess {
            primaryPlaces = it
        }.onFailure {
            _event.value = EventWrapper(MainFragment.Event.Error(R.string.failed_to_fetch_places))
        }
        _attendanceRequestingCount.decrease()
    }

    override suspend fun fetchPlaces() {
        _attendanceRequestingCount.increase()
        attendanceUseCase.getAllPlaces().onSuccess {
            places = it
        }.onFailure {
            _event.value = EventWrapper(MainFragment.Event.Error(R.string.failed_to_fetch_places))
        }
        _attendanceRequestingCount.decrease()
    }

    private suspend fun updateNotice() {
        noticeUseCase.getNotice().onSuccess {
            _noticeList.value = it
        }.onFailure {
            _noticeList.value = listOf(noticeUseCase.failedNotice)
        }
    }

    private suspend fun updateTodayMeal() {
        mealUseCase.getTodaysMeal().onSuccess {
            _todayMeal.value = it
        }.onFailure {
            _todayMeal.value = mealUseCase.failedMeal
        }
    }

    private suspend fun updateCurrentTimeCode() {
        configUseCase.getCurrentTimeCode().onSuccess {
            _currentTimeCode.value = it
        }.onFailure {
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
