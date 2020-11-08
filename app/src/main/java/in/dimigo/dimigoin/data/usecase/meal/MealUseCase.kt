package `in`.dimigo.dimigoin.data.usecase.meal

import `in`.dimigo.dimigoin.data.model.MealModel
import java.util.*

interface MealUseCase {
    suspend fun getTodaysMeal(calendar: Calendar): MealModel
}
