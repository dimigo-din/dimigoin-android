package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import `in`.dimigo.dimigoin.ui.item.MealItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainFragmentViewModel(private val mealUseCase: MealUseCase) : ViewModel() {
    private val _attendanceLocation = MutableLiveData(AttendanceLocation.Class)
    private val _todayMeal = MutableLiveData<MealItem>()
    val attendanceLocation: LiveData<AttendanceLocation> = _attendanceLocation
    val todayMeal: LiveData<MealItem> = _todayMeal

    init {
        fetchTodayMeal()
    }

    private fun fetchTodayMeal() = viewModelScope.launch {
        try {
            _todayMeal.value = mealUseCase.getTodaysMeal()
        } catch (e: Exception) {
            e.printStackTrace()
            _todayMeal.value = mealUseCase.failedMeal
        }
    }

    fun onAttendanceLocationButtonClicked(location: AttendanceLocation) {
        _attendanceLocation.value = location
    }
}
