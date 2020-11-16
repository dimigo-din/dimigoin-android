package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.data.model.MealModel
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import `in`.dimigo.dimigoin.data.util.UserDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainFragmentViewModel(
    private val mainViewUseCase: MainViewUseCase,
    private val mealUseCase: MealUseCase
) : ViewModel() {
    private val _attendanceLocation = MutableLiveData(AttendanceLocation.Class)
    private val _todayMeal = MutableLiveData<MealModel>()
    val attendanceLocation: LiveData<AttendanceLocation> = _attendanceLocation
    val todayMeal: LiveData<MealModel> = _todayMeal
    val userData = UserDataStore.userData

    init {
        fetchTodayMeal()
    }

    private fun fetchTodayMeal() = viewModelScope.launch {
        try {
            _todayMeal.value = mealUseCase.getTodaysMeal()
        } catch (e: Exception) {
            e.printStackTrace()
            mainViewUseCase.onMealFetchFailed()
        }
    }

    fun setTodayMeal(mealModel: MealModel) {
        _todayMeal.value = mealModel
    }

    fun onAttendanceLocationButtonClicked(location: AttendanceLocation) {
        _attendanceLocation.value = location
    }
}
