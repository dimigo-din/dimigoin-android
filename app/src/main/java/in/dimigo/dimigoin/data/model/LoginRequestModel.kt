package `in`.dimigo.dimigoin.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequestModel(
    val username: String,
    val password: String
)
