package `in`.dimigo.dimigoin.data.usecase.meal

import `in`.dimigo.dimigoin.data.api.DimigoinApi
import `in`.dimigo.dimigoin.data.model.MealModel
import retrofit2.await
import java.text.SimpleDateFormat
import java.util.*

class MealUseCaseImpl(private val api: DimigoinApi) : MealUseCase {
    override suspend fun getTodaysMeal(): MealModel {
        val calendar = Calendar.getInstance()
        val formattedDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(calendar.time)
        return api.getMeal(formattedDate).await()
    }
}
