package `in`.dimigo.dimigoin.data.usecase.meal

import `in`.dimigo.dimigoin.data.model.toMealItem
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.ui.item.MealItem
import retrofit2.await

class MealUseCaseImpl(private val service: DimigoinService, override val failedMeal: MealItem) : MealUseCase {

    override suspend fun getTodaysMeal(): MealItem {
        return service.getTodayMeal().await().toMealItem()
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
}
