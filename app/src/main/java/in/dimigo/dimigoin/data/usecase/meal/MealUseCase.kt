package `in`.dimigo.dimigoin.data.usecase.meal

import `in`.dimigo.dimigoin.ui.item.MealItem
import java.util.*

interface MealUseCase {
    val failedMeal: MealItem

    suspend fun getTodaysMeal(): MealItem

    suspend fun getWeeklyMeal(): List<MealItem>

    suspend fun getMeal(date: Date): MealItem
}
