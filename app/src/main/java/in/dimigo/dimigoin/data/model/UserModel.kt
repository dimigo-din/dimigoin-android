package `in`.dimigo.dimigoin.data.model

import com.google.gson.annotations.SerializedName

data class UserResponseModel(
    val identity: UserModel
)

data class UserModel(
    val idx: Int,
    val name: String,
    val grade: Int,
    @SerializedName("class") val klass: Int,
    val number: Int,
    val serial: Int,
    val photo: List<String>,
    val userType: String,
    val gender: String
)
