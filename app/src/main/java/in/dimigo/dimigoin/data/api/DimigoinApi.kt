package `in`.dimigo.dimigoin.data.api

import `in`.dimigo.dimigoin.data.model.AuthModel
import `in`.dimigo.dimigoin.data.model.LoginRequestModel
import `in`.dimigo.dimigoin.data.model.MealModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DimigoinApi {
    @POST("auth")
    fun login(@Body loginRequestModel: LoginRequestModel): Call<AuthModel>

    @GET("dimibobs/today/")
    fun getTodaysMeal(): Call<MealModel>

    companion object {
        const val BASE_URL = "https://api.dimigo.in"
    }
}
