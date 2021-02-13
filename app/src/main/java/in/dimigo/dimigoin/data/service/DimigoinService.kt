package `in`.dimigo.dimigoin.data.service

import `in`.dimigo.dimigoin.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface DimigoinService {
    // Auth
    @POST("/auth")
    fun login(@Body loginRequestModel: LoginRequestModel): Call<AuthModel>

    @POST("/auth/refresh")
    fun refreshToken(@Header("Authorization") refreshToken: String): Call<AuthModel>

    // User
    @GET("/user/me")
    fun getMyInfo(): Call<UserResponseModel>

    // Meal
    @GET("/meal/today")
    fun getTodayMeal(): Call<MealResponseModel>

    @GET("/meal/weekly")
    fun getWeeklyMeal(): Call<WeeklyMealResponseModel>

    // Ingang
    @GET("/ingang-application/status")
    fun getIngangStatus(): Call<IngangStatusModel>

    @POST("/ingang-application/time/{time}")
    fun applyIngang(@Path("time") time: IngangTime): Call<Void?>

    @DELETE("/ingang-application/time/{time}")
    fun cancelIngang(@Path("time") time: IngangTime): Call<Void?>

    // Timetable
    @GET("/timetable/weekly/grade/{grade}/class/{klass}")
    fun getWeeklyTimetable(@Path("grade") grade: Int, @Path("class") klass: Int): Call<TimetableResponseModel>

    companion object {
        const val BASE_URL = "https://api.dimigo.in"

        fun getProfileUrl(photo: String) = "https://api.dimigo.hs.kr/user_photo/$photo"
    }
}
