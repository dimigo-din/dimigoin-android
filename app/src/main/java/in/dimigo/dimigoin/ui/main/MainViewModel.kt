package `in`.dimigo.dimigoin.ui.main

import `in`.dimigo.dimigoin.data.model.MealModel
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val mealUseCase: MealUseCase) : ViewModel() {
    private val _todayMeal = MutableLiveData<MealModel>()
    val todayMeal: LiveData<MealModel> = _todayMeal

    fun fetchTodayMeal() {
        viewModelScope.launch {
            _todayMeal.value = mealUseCase.getTodaysMeal()
        }
    }
}
