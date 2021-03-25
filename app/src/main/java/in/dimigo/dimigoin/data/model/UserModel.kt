package `in`.dimigo.dimigoin.data.model

import `in`.dimigo.dimigoin.R
import android.content.Context
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
    val gender: String,
    @SerializedName("birthdate") val birthDate: String?,
    val libraryId: String?,
    private val userType: UserType,
    private val permissions: List<String> = listOf()
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
    @SerializedName("T")
    TEACHER,

    @SerializedName("S")
    STUDENT
}

enum class Permission(val permissionName: String) {
    ATTENDANCE("attendance")
}
