package `in`.dimigo.dimigoin.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DailyTimetableModel(
    val sequence: Array<String?>,
    val date: String,
)

@JsonClass(generateAdapter = true)
data class TimetableResponseModel(
    @Json(name = "timetable") val dailyTimetables: List<DailyTimetableModel>
)
