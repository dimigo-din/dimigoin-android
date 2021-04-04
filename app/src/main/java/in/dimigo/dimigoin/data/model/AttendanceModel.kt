package `in`.dimigo.dimigoin.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class AttendanceLogsResponseModel(
    val logs: List<AttendanceLogModel>
)

@JsonClass(generateAdapter = true)
data class AttendanceLogModel(
    val student: UserModel,
    val date: Date,
    val place: PlaceModel,
    val remark: String?,
    val updatedBy: UserModel? = null,
    @Json(name = "updatedAt") val time: Date,
)

@JsonClass(generateAdapter = true)
data class AttendanceLogRequestModel(
    @Json(name = "place") val placeId: String,
    val remark: String?
)

@JsonClass(generateAdapter = true)
data class AttendanceStatusModel(
    val student: UserModel,
    val log: AttendanceStatusLogModel?
)

@JsonClass(generateAdapter = true)
data class AttendanceStatusLogModel(
    val student: String,
    val date: Date,
    val place: PlaceModel,
    val remark: String?,
    @Json(name = "updatedAt") val time: Date
)

@JsonClass(generateAdapter = true)
data class AttendanceStatusResponseModel(
    val status: List<AttendanceStatusModel>
)
