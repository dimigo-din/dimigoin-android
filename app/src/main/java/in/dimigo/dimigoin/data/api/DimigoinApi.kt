package `in`.dimigo.dimigoin.data.api

import `in`.dimigo.dimigoin.data.model.AuthModel
import `in`.dimigo.dimigoin.data.model.LoginRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DimigoinApi {
    @POST("auth")
    fun login(@Body loginRequestModel: LoginRequestModel): Call<AuthModel>

    companion object {
        const val BASE_URL = "https://api.dimigo.in"
    }
}
