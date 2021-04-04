package `in`.dimigo.dimigoin.data.model

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class NoticeResponseModel(
    val notices: List<NoticeModel>
)

@JsonClass(generateAdapter = true)
data class NoticeModel(
    val title: String,
    val content: String,
    val author: UserModel,
    val targetGrade: List<Int>,
    val startDate: Date,
    val endDate: Date
)
