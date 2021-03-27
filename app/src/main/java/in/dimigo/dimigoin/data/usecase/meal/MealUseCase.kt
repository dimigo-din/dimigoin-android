package `in`.dimigo.dimigoin.data.usecase.meal

import `in`.dimigo.dimigoin.data.util.Result
import `in`.dimigo.dimigoin.ui.item.MealItem

interface MealUseCase {
    val failedMeal: MealItem

    suspend fun getTodaysMeal(): Result<MealItem>
    suspend fun getWeeklyMeal(): Result<List<MealItem>>
}
