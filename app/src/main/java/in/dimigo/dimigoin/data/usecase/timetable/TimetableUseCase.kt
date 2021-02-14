package `in`.dimigo.dimigoin.data.usecase.timetable

import `in`.dimigo.dimigoin.ui.item.SubjectItem

interface TimetableUseCase {
    suspend fun getWeeklyTimetable(): List<SubjectItem?>
}

