package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.data.model.PrimaryPlaceModel
import `in`.dimigo.dimigoin.data.usecase.attendance.AttendanceUseCase
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import `in`.dimigo.dimigoin.data.usecase.notice.NoticeUseCase
import `in`.dimigo.dimigoin.ui.item.MealItem
import `in`.dimigo.dimigoin.ui.item.NoticeItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainFragmentViewModel(
    private val mealUseCase: MealUseCase,
    private val attendanceUseCase: AttendanceUseCase,
    private val noticeUseCase: NoticeUseCase
) : ViewModel() {
    private val _attendanceLocation = MutableLiveData(AttendanceLocation.Class)
    val attendanceLocation: LiveData<AttendanceLocation> = _attendanceLocation
    private val _todayMeal = MutableLiveData<MealItem>()
    val todayMeal: LiveData<MealItem> = _todayMeal
    private val _notice = MutableLiveData<NoticeItem>()
    val notice: LiveData<NoticeItem> = _notice

    private var primaryPlaces: List<PrimaryPlaceModel>? = null

    init {
        viewModelScope.launch {
            updateCurrentLocation()
        }
        updateNotice()
        updateTodayMeal()
    }

    fun onAttendanceLocationButtonClicked(location: AttendanceLocation) = viewModelScope.launch {
        // TODO : 같은 버튼 클릭 무시, 로딩중 클릭 방지
        if (primaryPlaces == null) fetchPrimaryPlaces()
        val place = location.getPrimaryPlace(primaryPlaces)
        try {
            attendanceUseCase.changeCurrentAttendancePlace(place ?: throw Exception("Place is null"))
            updateCurrentLocation()
        } catch (e: Exception) {
            e.printStackTrace()
            // TODO 에러 처리
        }
    }

    private suspend fun updateCurrentLocation() {
        if (primaryPlaces == null) fetchPrimaryPlaces()
        try {
            val primaryPlaces = primaryPlaces ?: throw Exception("Variable primaryPlaces is null")
            val currentPlace = attendanceUseCase.getCurrentAttendancePlace()
                .toPrimaryPlaceModel(primaryPlaces)
            val location = AttendanceLocation.fromPrimaryPlace(currentPlace)
            _attendanceLocation.value = location
        } catch (e: Exception) {
            e.printStackTrace()
            // TODO
        }
    }

    private suspend fun fetchPrimaryPlaces() {
        try {
            primaryPlaces = attendanceUseCase.getPrimaryPlaces()
        } catch (e: Exception) {
            e.printStackTrace()
            // TODO
        }
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
}
