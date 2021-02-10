package `in`.dimigo.dimigoin.data.model

import `in`.dimigo.dimigoin.ui.item.IngangStatusItem

data class IngangStatusModel(
    val weeklyTicketCount: Int,
    val weeklyUsedTicket: Int,
    val weeklyRemainTicket: Int,
    val ingangMaxApplier: Int,
    val applicationsInClass: List<IngangApplicationModel>
) {
    fun toIngangStatusItem() = IngangStatusItem(
        weeklyTicketCount,
        weeklyUsedTicket,
        weeklyRemainTicket,
        ingangMaxApplier,
        applicationsInClass.filter { it.time == IngangTime.NSS1 },
        applicationsInClass.filter { it.time == IngangTime.NSS2 }
    )
}

data class IngangApplicationModel(
    val date: String,
    val time: IngangTime,
    val applier: UserModel
)

enum class IngangTime {
    // Night Self Study
    NSS1,
    NSS2
}
