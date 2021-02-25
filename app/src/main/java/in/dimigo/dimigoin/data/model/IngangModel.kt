package `in`.dimigo.dimigoin.data.model

import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.ui.item.IngangStatusItem

data class IngangStatusModel(
    val weeklyTicketCount: Int,
    val weeklyUsedTicket: Int,
    val weeklyRemainTicket: Int,
    val ingangMaxApplier: Int,
    val applicationsInClass: List<IngangApplicationModel>,
    val ingangApplyPeriod: TimePeriodModel,
    val selfStudyTimes: Map<String, TimePeriodModel>
) {
    fun toIngangStatusItem(): IngangStatusItem {
        val time1Applications = applicationsInClass.filter { it.time == IngangTime.NSS1 }
        val time2Applications = applicationsInClass.filter { it.time == IngangTime.NSS2 }
        val predicate = { application: IngangApplicationModel ->
            application.applier.idx == UserDataStore.userData.idx
        }
        val time1Applied = time1Applications.any(predicate)
        val time2Applied = time2Applications.any(predicate)
        return IngangStatusItem(
            weeklyTicketCount,
            weeklyUsedTicket,
            weeklyRemainTicket,
            ingangMaxApplier,
            ingangApplyPeriod,
            selfStudyTimes["NSS1"],
            selfStudyTimes["NSS2"],
            time1Applied,
            time2Applied,
            time1Applications,
            time2Applications
        )
    }
}

data class IngangApplicationModel(
    val date: String,
    val time: IngangTime,
    val applier: UserModel
)

enum class IngangTime(val timeNumber: Int) {
    // Night Self Study
    NSS1(1),
    NSS2(2)
}
