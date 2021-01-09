package `in`.dimigo.dimigoin.data.usecase.meal

import `in`.dimigo.dimigoin.data.api.MealApi
import `in`.dimigo.dimigoin.data.model.MealModel
import retrofit2.await
import java.text.SimpleDateFormat
import java.util.*

class MealUseCaseImpl(private val api: MealApi, override val failedMeal: MealModel) : MealUseCase {
    override suspend fun getTodaysMeal(): MealModel {
        val calendar = Calendar.getInstance()
        val formattedDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(calendar.time)
        return api.getMeal(formattedDate).await()
    }

    override suspend fun getMeal(date: Date): MealModel {
        val formattedDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(date)
        return api.getMeal(formattedDate).await()
    }
}
