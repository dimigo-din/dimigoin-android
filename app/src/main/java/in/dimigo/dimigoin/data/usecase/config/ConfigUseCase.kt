package `in`.dimigo.dimigoin.data.usecase.config

import `in`.dimigo.dimigoin.data.model.MealTimesModel
import `in`.dimigo.dimigoin.data.util.Result

interface ConfigUseCase {
    suspend fun getCurrentTimeCode(): Result<String>
    suspend fun getMealTimes(): Result<MealTimesModel>
}
