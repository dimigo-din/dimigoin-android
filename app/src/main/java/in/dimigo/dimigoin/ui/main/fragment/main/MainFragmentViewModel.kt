package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.data.model.MealModel
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainFragmentViewModel(mealUseCase: MealUseCase) : ViewModel() {
    private val _attendanceLocation = MutableLiveData(AttendanceLocation.Class)
    private val _todayMeal = MutableLiveData<MealModel>()
    val attendanceLocation: LiveData<AttendanceLocation> = _attendanceLocation
    val todayMeal: LiveData<MealModel> = _todayMeal

    init {
        viewModelScope.launch {
            _todayMeal.value = mealUseCase.getTodaysMeal()
        }
    }

    fun onAttendanceLocationButtonClicked(location: AttendanceLocation) {
        _attendanceLocation.value = location
    }
}
