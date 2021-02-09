package `in`.dimigo.dimigoin.data.model

data class IngangStatusModel(
    val weeklyTicketCount: Int,
    val weeklyUsedTicket: Int,
    val ingangMaxApplier: Int,
    val applicationsInClass: List<IngangApplicationModel>
)

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
