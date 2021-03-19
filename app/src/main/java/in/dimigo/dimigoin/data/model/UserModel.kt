package `in`.dimigo.dimigoin.data.model

import `in`.dimigo.dimigoin.R
import android.content.Context
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserResponseModel(
    val identity: UserModel
)

@Parcelize
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
    val libraryId: String?,
    private val permissions: List<String> = listOf()
) : Parcelable {

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
