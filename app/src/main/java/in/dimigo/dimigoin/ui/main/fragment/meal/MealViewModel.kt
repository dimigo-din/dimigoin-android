package `in`.dimigo.dimigoin.ui.main.fragment.meal

import `in`.dimigo.dimigoin.data.model.MealModel
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

class MealViewModel(private val useCase: MealUseCase) : ViewModel() {
    private val _meal = MutableLiveData<MealModel>()
    val date = MutableLiveData<Date>()
    val meal: LiveData<MealModel> = _meal

    init {
        val time = Calendar.getInstance().time
        viewModelScope.launch {
            updateMeal(time)
        }
    }

    suspend fun updateMeal(time: Date) {
        date.value = time
        _meal.value = useCase.getMeal(time)
    }
}
