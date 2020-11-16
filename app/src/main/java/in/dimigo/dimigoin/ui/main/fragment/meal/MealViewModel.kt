package `in`.dimigo.dimigoin.ui.main.fragment.meal

import `in`.dimigo.dimigoin.data.model.MealModel
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MealViewModel(
    private val mealViewUseCase: MealViewUseCase,
    private val mealUseCase: MealUseCase
) : ViewModel() {

    private val _todayMeal = MutableLiveData<MealModel>()
    val todayMeal: LiveData<MealModel> = _todayMeal

    init {
        fetchTodayMeal()
    }

    private fun fetchTodayMeal() = viewModelScope.launch {
        try {
            _todayMeal.value = mealUseCase.getTodaysMeal()
        } catch (e: Exception) {
            e.printStackTrace()
            mealViewUseCase.onMealFetchFailed()
        }
    }

    fun setTodayMeal(mealModel: MealModel) {
        _todayMeal.value = mealModel
    }
}
