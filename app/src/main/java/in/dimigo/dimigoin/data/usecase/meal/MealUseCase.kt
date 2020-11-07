package `in`.dimigo.dimigoin.data.usecase.meal

import `in`.dimigo.dimigoin.data.model.MealModel

interface MealUseCase {
    suspend fun getTodaysMeal(): MealModel
}
