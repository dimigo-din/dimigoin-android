package `in`.dimigo.dimigoin.data.model

import com.squareup.moshi.Json
import java.util.*

data class AttendanceLogsResponseModel(
    val logs: List<AttendanceLogModel>
)

data class AttendanceLogModel(
    val student: UserModel,
    val date: Date,
    val place: PlaceModel,
    val remark: String?,
    val updatedBy: UserModel? = null,
    @field:Json(name = "updatedAt") val time: Date,
)

data class AttendanceLogRequestModel(
    @field:Json(name = "place") val placeId: String,
    val remark: String?
)

data class AttendanceStatusModel(
    val student: UserModel,
    val log: AttendanceStatusLogModel?
)

data class AttendanceStatusLogModel(
    val student: String,
    val date: Date,
    val place: PlaceModel,
    val remark: String?,
    @field:Json(name = "updatedAt") val time: Date
)

data class AttendanceStatusResponseModel(
    val status: List<AttendanceStatusModel>
)
