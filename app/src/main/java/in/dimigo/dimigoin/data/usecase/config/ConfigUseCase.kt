package `in`.dimigo.dimigoin.data.usecase.config

import `in`.dimigo.dimigoin.data.model.MealTimesModel

interface ConfigUseCase {
    suspend fun getCurrentTimeCode(): String
    suspend fun getMealTimes(): MealTimesModel
}
