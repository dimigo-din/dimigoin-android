package `in`.dimigo.dimigoin.data.model

import com.google.gson.annotations.SerializedName

class DailyTimetableModel(
    val sequence: Array<String?>,
    val date: String,
)

data class TimetableResponseModel(
    @SerializedName("timetable") val dailyTimetables: List<DailyTimetableModel>
)
