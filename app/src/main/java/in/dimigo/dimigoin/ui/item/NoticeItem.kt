package `in`.dimigo.dimigoin.ui.item

import `in`.dimigo.dimigoin.data.model.UserModel

data class NoticeItem(
    val title: String?,
    val content: String,
    val author: UserModel?
)
