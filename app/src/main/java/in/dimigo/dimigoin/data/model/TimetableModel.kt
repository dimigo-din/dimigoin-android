package `in`.dimigo.dimigoin.data.model

class TimetableModel(
    val sequence: List<String>,
    val date: String,
)

data class TimetableResponseModel(
    val timetable: List<TimetableModel>
)
