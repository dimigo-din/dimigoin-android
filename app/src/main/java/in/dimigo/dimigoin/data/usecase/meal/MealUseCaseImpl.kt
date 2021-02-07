package `in`.dimigo.dimigoin.data.usecase.meal

import `in`.dimigo.dimigoin.data.model.toMealItem
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.ui.item.MealItem
import retrofit2.await
import java.text.SimpleDateFormat
import java.util.*

class MealUseCaseImpl(private val service: DimigoinService, override val failedMeal: MealItem) : MealUseCase {

    override suspend fun getTodaysMeal(): MealItem {
        val calendar = Calendar.getInstance()
        return getMeal(calendar.time)
    }

    override suspend fun getWeeklyMeal(): List<MealItem> {
        val meals = service.getWeeklyMeal().await().meals.map { it.toMealItem() }.toMutableList()
        repeat(7) { i ->
            meals.getOrNull(i) ?: run {
                meals[i] = failedMeal
            }
        }
        return meals
    }

    override suspend fun getMeal(date: Date): MealItem {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
        return service.getMeal(formattedDate).await().toMealItem()
    }
}
