package `in`.dimigo.dimigoin.data.model

import com.google.gson.annotations.SerializedName

data class UserResponseModel(
    val identity: UserModel
)

data class UserModel(
    val _id: String,
    val idx: Int,
    val name: String,
    val grade: Int,
    @SerializedName("class") val klass: Int,
    val number: Int,
    val serial: Int,
    val photos: List<String>,
    val userType: UserType,
    val gender: String,
    @SerializedName("birthdate") val birthDate: String?,
    val libraryId: String?
) {
    fun getDefaultClassName() = "${grade}학년 ${klass}반"
}

enum class UserType {
    @SerializedName("T")
    TEACHER,

    @SerializedName("S")
    STUDENT
}
