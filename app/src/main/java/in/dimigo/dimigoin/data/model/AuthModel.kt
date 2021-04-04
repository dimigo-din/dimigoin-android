package `in`.dimigo.dimigoin.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthModel(
    val accessToken: String,
    val refreshToken: String
)
