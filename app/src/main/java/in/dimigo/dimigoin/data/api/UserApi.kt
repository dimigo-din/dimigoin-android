package `in`.dimigo.dimigoin.data.api

import `in`.dimigo.dimigoin.data.model.UserResponseModel
import retrofit2.Call
import retrofit2.http.GET

interface UserApi {
    @GET("/user/me")
    fun getMyInfo(): Call<UserResponseModel>
}
