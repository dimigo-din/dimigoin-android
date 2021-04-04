package `in`.dimigo.dimigoin.data.model

import `in`.dimigo.dimigoin.R
import android.content.Context
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponseModel(
    val identity: UserModel
)

@JsonClass(generateAdapter = true)
data class UserModel(
    val _id: String,
    val idx: Int,
    val name: String,
    val grade: Int = 0,
    @Json(name = "class") val klass: Int = 0,
    val number: Int = 0,
    val serial: Int = 0,
    val photos: List<String> = listOf(),
    @Json(name = "birthdate") val birthDate: String?,
    val libraryId: String?,
    val userType: UserType = UserType.STUDENT,
    val permissions: List<String> = listOf()
) {

    fun getDefaultClassName(context: Context) = context.getString(R.string.format_student_info).format(grade, klass)

    fun getDepartmentName(context: Context): String {
        val departments = context.resources.getStringArray(R.array.departments)
        return when (klass) {
            1 -> departments[0]
            2 -> departments[1]
            3, 4 -> departments[2]
            5, 6 -> departments[3]
            else -> departments[0]
        }
    }

    fun isTeacher() = userType == UserType.TEACHER
    fun isStudent() = userType == UserType.STUDENT

    fun hasPermission(permission: Permission) = permissions.contains(permission.permissionName)
}

enum class UserType {
    @Json(name = "T")
    TEACHER,

    @Json(name = "S")
    STUDENT
}

enum class Permission(val permissionName: String) {
    ATTENDANCE("attendance")
}
