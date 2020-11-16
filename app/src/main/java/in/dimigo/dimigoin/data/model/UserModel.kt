package `in`.dimigo.dimigoin.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    val idx: Int,
    val name: String,
    val grade: String,
    @SerializedName("klass") val `class`: String,
    val number: String,
    val serial: String,
    val photo: String,
    val email: String,
    @SerializedName("user_type") val userType: String
)
