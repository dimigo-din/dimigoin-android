package `in`.dimigo.dimigoin.data.usecase.meal

import `in`.dimigo.dimigoin.data.api.DimigoinApi
import `in`.dimigo.dimigoin.data.model.MealModel
import retrofit2.await

class MealUseCaseImpl(private val api: DimigoinApi) : MealUseCase {
    override suspend fun getTodaysMeal(): MealModel {
        return api.getTodaysMeal().await()
    }
}
