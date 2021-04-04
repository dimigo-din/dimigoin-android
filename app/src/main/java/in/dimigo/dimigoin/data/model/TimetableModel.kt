package `in`.dimigo.dimigoin.data.model

import com.squareup.moshi.Json

class DailyTimetableModel(
    val sequence: Array<String?>,
    val date: String,
)

data class TimetableResponseModel(
    @field:Json(name = "timetable") val dailyTimetables: List<DailyTimetableModel>
)
