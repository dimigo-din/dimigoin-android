package `in`.dimigo.dimigoin.ui.item

import `in`.dimigo.dimigoin.data.model.AttendanceLogModel
import `in`.dimigo.dimigoin.data.model.UserModel
import `in`.dimigo.dimigoin.ui.main.fragment.main.AttendanceLocation
import java.text.DecimalFormat
import java.time.LocalDateTime

data class AttendanceItem(
    val student: UserModel,
    val location: AttendanceLocation?,
    val placeName: String?,
    val updatedAt: LocalDateTime?
) {
    fun formatNumber(): String = DecimalFormat("00").format(student.number)
}

data class AttendanceDetailItem(
    val student: UserModel,
    val logs: List<AttendanceLogModel>?
)
