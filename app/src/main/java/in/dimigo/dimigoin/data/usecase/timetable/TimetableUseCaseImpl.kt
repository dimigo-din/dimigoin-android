package `in`.dimigo.dimigoin.data.usecase.timetable

import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.ui.item.SubjectItem
import retrofit2.await

class TimetableUseCaseImpl(private val service: DimigoinService) : TimetableUseCase {
    private val userData = UserDataStore.userData

    override suspend fun getWeeklyTimetable(): List<SubjectItem?> {
        val timetables = service.getWeeklyTimetable(userData.grade, userData.klass).await().dailyTimetables
        val subjects = mutableListOf<SubjectItem?>()

        repeat(7) { i ->
            repeat(5) { j ->
                if (timetables[j].sequence.size > i)
                    subjects.add(SubjectItem(timetables[j].sequence[i]))
                else
                    subjects.add(null)
            }
        }

        return subjects
    }
}
