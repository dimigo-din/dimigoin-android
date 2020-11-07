package `in`.dimigo.dimigoin.data.model

import com.google.gson.annotations.SerializedName

data class AuthModel(
    val token: String,
    @SerializedName("refresh_token") val refreshToken: String
)
