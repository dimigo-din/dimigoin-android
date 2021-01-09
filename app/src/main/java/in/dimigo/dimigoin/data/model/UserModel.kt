package `in`.dimigo.dimigoin.data.model

data class UserResponseModel(
    val identity: UserModel
)

data class UserModel(
    val idx: Int,
    val name: String,
    val grade: Int,
    val `class`: Int,
    val number: Int,
    val serial: Int,
    val photo: List<String>,
    val userType: String,
    val gender: String
)
