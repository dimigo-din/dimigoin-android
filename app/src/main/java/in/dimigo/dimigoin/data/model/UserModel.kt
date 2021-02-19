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
    val photos: List<String>,
    val userType: UseType,
    val gender: String
)

enum class UseType {
    @SerializedName("T")
    TEACHER,

    @SerializedName("S")
    STUDENT
}
