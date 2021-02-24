package `in`.dimigo.dimigoin.ui.main.fragment.meal

import `in`.dimigo.dimigoin.data.model.MealTimesModel
import `in`.dimigo.dimigoin.data.usecase.config.ConfigUseCase
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import `in`.dimigo.dimigoin.ui.item.MealItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MealViewModel(
    private val mealUseCase: MealUseCase,
    private val configUseCase: ConfigUseCase
) : ViewModel() {
    private val _weeklyMeals = MutableLiveData<List<MealItem>>()
    val weeklyMeals: LiveData<List<MealItem>> = _weeklyMeals
    private val _mealTimes = MutableLiveData<MealTimesModel>()
    val mealTimes: LiveData<MealTimesModel> = _mealTimes

    init {
        fetchWeeklyMeals()
        fetchMealTimes()
    }

    private fun fetchWeeklyMeals() = viewModelScope.launch {
        try {
            _weeklyMeals.value = mealUseCase.getWeeklyMeal()
        } catch (e: Exception) {
            e.printStackTrace()
            val failedData = Array(7) { mealUseCase.failedMeal }
            _weeklyMeals.value = failedData.toList()
        }
    }

    private fun fetchMealTimes() = viewModelScope.launch {
        try {
            _mealTimes.value = configUseCase.getMealTimes()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
