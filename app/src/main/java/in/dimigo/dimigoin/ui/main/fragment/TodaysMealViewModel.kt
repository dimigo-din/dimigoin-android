package `in`.dimigo.dimigoin.ui.main.fragment

import `in`.dimigo.dimigoin.data.model.MealModel
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class TodaysMealViewModel(private val useCase: MealUseCase) : ViewModel() {
    private val _meal = MutableLiveData<MealModel>()
    private val _time = MutableLiveData<Int>()

    val meal: LiveData<MealModel> = _meal
    val time: LiveData<Int> = _time

    suspend fun getTodaysMeal() {
        try {
            _meal.value = useCase.getTodaysMeal()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        _time.value = getCurrentTime()
    }

    private fun getCurrentTime(): Int {
        val calendar = Calendar.getInstance()
        return when (calendar[Calendar.HOUR_OF_DAY]) {
            in 0..8 -> 0
            in 9..13 -> 1
            in 14..19 -> 2
            else -> 0
        }
    }
}
