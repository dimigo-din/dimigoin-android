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
    @GET("/timetable/weekly/grade/{grade}/class/{class}")
    fun getWeeklyTimetable(@Path("grade") grade: Int, @Path("class") klass: Int): Call<TimetableResponseModel>

    // Place
    @GET("/place")
    fun getAllPlaces(): Call<PlacesResponseModel>

    @GET("/place/primary")
    fun getPrimaryPlaces(): Call<PrimaryPlacesResponseModel>

    // Attendance
    @GET("/attendance")
    fun getTodayAttendanceLogs(): Call<AttendanceLogsResponseModel>

    @POST("/attendance")
    fun createAttendanceLog(@Body attendanceLogRequestModel: AttendanceLogRequestModel): Call<Void?>

    @POST("/attendance/student/{studentId}")
    fun createAttendanceLogOfStudent(
        @Path("studentId") studentId: String,
        @Body attendanceLogRequestModel: AttendanceLogRequestModel
    ): Call<Void?>

    @GET("/attendance/date/{date}/grade/{grade}/class/{klass}/status")
    fun getAttendanceStatus(
        @Path("date") date: String,
        @Path("grade") grade: Int,
        @Path("klass") klass: Int
    ): Call<AttendanceStatusResponseModel>

    @GET("/attendance/date/{date}/grade/{grade}/class/{klass}/timeline")
    fun getAttendanceTimeline(
        @Path("date") date: String,
        @Path("grade") grade: Int,
        @Path("klass") klass: Int
    ): Call<AttendanceLogsResponseModel>

    @GET("/attendance/date/{date}/student/{studentId}")
    fun getSpecificAttendanceLogs(
        @Path("date") date: String,
        @Path("studentId") studentId: String
    ): Call<AttendanceLogsResponseModel>

    // Notice
    @GET("/notice/current")
    fun getCurrentNotices(): Call<NoticeResponseModel>

    // FCM
    @POST("/fcm/token")
    fun uploadFcmToken(@Body fcmTokenUploadRequestModel: FcmTokenUploadRequestModel): Call<Void?>

    @HTTP(method = "DELETE", path = "/fcm/token", hasBody = true)
    fun deleteFcmToken(@Body fcmTokenUploadRequestModel: FcmTokenUploadRequestModel): Call<Void?>

    // Config
    @GET("/config")
    fun getConfigs(): Call<ConfigResponseModel>

    companion object {
        const val BASE_URL = "https://api.dimigo.in"
    }
}
