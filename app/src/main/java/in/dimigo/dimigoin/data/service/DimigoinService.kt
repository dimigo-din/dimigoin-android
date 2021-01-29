package `in`.dimigo.dimigoin.data.service

import `in`.dimigo.dimigoin.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface DimigoinService {
    @POST("/auth")
    fun login(@Body loginRequestModel: LoginRequestModel): Call<AuthModel>

    @POST("/auth/refresh")
    fun refreshToken(@Header("Authorization") refreshToken: String): Call<AuthModel>

    @GET("/user/me")
    fun getMyInfo(): Call<UserResponseModel>

    @GET("/meal/{date}")
    fun getMeal(@Path("date") date: String): Call<MealResponseModel>

    @GET("/meal/weekly")
    fun getWeeklyMeal(): Call<WeeklyMealResponseModel>

    companion object {
        const val BASE_URL = "http://edison.dimigo.hs.kr"

        fun getProfileUrl(photo: String) = "https://api.dimigo.hs.kr/user_photo/$photo"
    }
}
