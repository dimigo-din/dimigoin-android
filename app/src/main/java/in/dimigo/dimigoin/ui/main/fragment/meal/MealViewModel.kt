package `in`.dimigo.dimigoin.ui.main.fragment.meal

import `in`.dimigo.dimigoin.data.model.MealModel
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MealViewModel(private val useCase: MealUseCase) : ViewModel() {
    private val _meal = MutableLiveData<MealModel>()
    val meal: LiveData<MealModel> = _meal

    init {
        updateMeal()
    }

    private fun updateMeal() = viewModelScope.launch {
        _meal.value = useCase.getTodaysMeal()
    }
}
