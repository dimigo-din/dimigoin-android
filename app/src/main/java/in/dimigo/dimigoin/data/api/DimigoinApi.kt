package `in`.dimigo.dimigoin.data.api

import `in`.dimigo.dimigoin.data.model.AuthModel
import `in`.dimigo.dimigoin.data.model.LoginRequestModel
import `in`.dimigo.dimigoin.data.model.MealModel
import retrofit2.Call
import retrofit2.http.*

interface DimigoinApi {
    @POST("auth")
    fun login(@Body loginRequestModel: LoginRequestModel): Call<AuthModel>

    @POST("auth/token/refresh")
    fun refreshToken(@Header("Authorization") refreshToken: String): Call<AuthModel>

    @GET("dimibobs/{date}")
    fun getMeal(@Path("date") date: String): Call<MealModel>

    companion object {
        const val BASE_URL = "https://api.dimigo.in"

        fun getProfileUrl(photo: String) = "https://api.dimigo.hs.kr/user_photo/$photo"
    }
}
