package `in`.dimigo.dimigoin.data.model

import java.util.*

data class NoticeResponseModel(
    val notices: List<NoticeModel>
)

data class NoticeModel(
    val title: String,
    val content: String,
    val author: UserModel,
    val targetGrade: List<Int>,
    val startDate: Date,
    val endDate: Date
)
