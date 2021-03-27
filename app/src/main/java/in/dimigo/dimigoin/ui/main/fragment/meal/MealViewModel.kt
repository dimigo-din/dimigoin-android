package `in`.dimigo.dimigoin.ui.main.fragment.meal

import `in`.dimigo.dimigoin.data.model.MealTimesModel
import `in`.dimigo.dimigoin.data.usecase.config.ConfigUseCase
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import `in`.dimigo.dimigoin.ui.item.MealItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class MealViewModel(
    private val mealUseCase: MealUseCase,
    private val configUseCase: ConfigUseCase
) : ViewModel() {
    private val _weeklyMeals = MutableLiveData<List<MealItem>>()
    val weeklyMeals: LiveData<List<MealItem>> = _weeklyMeals
    private val _mealTimes = MutableLiveData<MealTimesModel>()
    val mealTimes: LiveData<MealTimesModel> = _mealTimes
    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    init {
        refresh(true)
    }

    fun refresh(isInitial: Boolean) = viewModelScope.launch {
        if (!isInitial) _isRefreshing.value = true
        awaitAll(
            async { fetchWeeklyMeals() },
            async { fetchMealTimes() }
        )
        if (!isInitial) _isRefreshing.value = false
    }

    private suspend fun fetchWeeklyMeals() {
        try {
            _weeklyMeals.value = mealUseCase.getWeeklyMeal()
        } catch (e: Exception) {
            e.printStackTrace()
            val failedData = Array(7) { mealUseCase.failedMeal }
            _weeklyMeals.value = failedData.toList()
        }
    }

    private suspend fun fetchMealTimes() {
        configUseCase.getMealTimes().onSuccess {
            _mealTimes.value = it
        }
    }
}
