package `in`.dimigo.dimigoin.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class AttendanceLogsResponseModel(
    val logs: List<AttendanceLogModel>
)

data class AttendanceLogModel(
    val student: UserModel,
    val date: String,
    val place: PlaceModel,
    val remark: String,
    @SerializedName("updatedAt") val time: Date
)

data class AttendanceLogRequestModel(
    @SerializedName("place") val placeId: String,
    val remark: String
)
