package `in`.dimigo.dimigoin.ui.item

import `in`.dimigo.dimigoin.data.model.IngangApplicationModel

data class IngangStatusItem(
    val weeklyTicketCount: Int,
    val weeklyUsedTicket: Int,
    val weeklyRemainTicket: Int,
    val ingangMaxApplier: Int,
    val time1Applications: List<IngangApplicationModel>,
    val time2Applications: List<IngangApplicationModel>
)
