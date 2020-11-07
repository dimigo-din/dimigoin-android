package `in`.dimigo.dimigoin.ui.main.fragment

import `in`.dimigo.dimigoin.data.model.MealModel
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodaysMealViewModel(private val useCase: MealUseCase) : ViewModel() {
    val meal = MutableLiveData<MealModel>()

    suspend fun getTodaysMeal() {
        meal.value = useCase.getTodaysMeal()
    }
}
