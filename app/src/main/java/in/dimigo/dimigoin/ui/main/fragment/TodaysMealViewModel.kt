package `in`.dimigo.dimigoin.ui.main.fragment

import `in`.dimigo.dimigoin.data.model.MealModel
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class TodaysMealViewModel(private val useCase: MealUseCase) : ViewModel() {
    val meal = MutableLiveData<MealModel>()
    var time = MutableLiveData<Int>()

    suspend fun getTodaysMeal() {
        meal.value = useCase.getTodaysMeal()
        time.value = getCurrentTime()
        Log.d("Time", "time: $time")
    }

    private fun getCurrentTime(): Int {
        return when (Calendar.getInstance()[Calendar.HOUR_OF_DAY]) {
            in 0..8 -> 0
            in 9..13 -> 1
            in 14..19 -> 2
            else -> 0
        }
    }
}
