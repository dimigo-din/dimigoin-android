package `in`.dimigo.dimigoin.data.usecase.meal

import `in`.dimigo.dimigoin.data.api.MealApi
import `in`.dimigo.dimigoin.ui.item.MealItem
import retrofit2.await
import java.text.SimpleDateFormat
import java.util.*

class MealUseCaseImpl(private val api: MealApi, override val failedMeal: MealItem) : MealUseCase {
    override suspend fun getTodaysMeal(): MealItem {
        val calendar = Calendar.getInstance()
        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        return api.getMeal(formattedDate).await().toMealItem()
    }

    override suspend fun getMeal(date: Date): MealItem {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
        return api.getMeal(formattedDate).await().toMealItem()
    }
}
