package `in`.dimigo.dimigoin.data.usecase.timetable

import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.DateUtil
import `in`.dimigo.dimigoin.data.util.Result
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.data.util.safeApiCall
import `in`.dimigo.dimigoin.ui.item.SubjectItem
import retrofit2.await
import java.time.DayOfWeek
import java.time.LocalDate

class TimetableUseCaseImpl(
    private val service: DimigoinService,
    private val userDataStore: UserDataStore
) : TimetableUseCase {

    override suspend fun getWeeklyTimetable(): Result<List<SubjectItem?>> = safeApiCall {
        val grade = userDataStore.userData?.grade ?: 0
        val klass = userDataStore.userData?.klass ?: 0
        val timetables = service.getWeeklyTimetable(grade, klass)
            .await()
            .dailyTimetables

        if (timetables.isEmpty()) return@safeApiCall List(35) { null }

        val fastestDate = LocalDate.parse(timetables.first().date)

        return@safeApiCall List(5) {
            fastestDate.with(DayOfWeek.MONDAY).plusDays(it.toLong()).format(DateUtil.dateFormatter)
        }.map {
            timetables.find { timetable -> timetable.date == it }?.sequence ?: arrayOf()
        }.map {
            it.plus(arrayOfNulls(7)).take(7)
        }.let {
            List(7) { time ->
                List(5) { day ->
                    SubjectItem(it[day][time], DayOfWeek.of(day + 1))
                }
            }.flatMap { it }
        }
    }
}
