package `in`.dimigo.dimigoin.data.api

import `in`.dimigo.dimigoin.data.model.MealModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MealApi {
    @GET("/meal/{date}")
    fun getMeal(@Path("date") date: String): Call<MealModel>
}
