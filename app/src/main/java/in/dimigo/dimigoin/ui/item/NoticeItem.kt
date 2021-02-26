package `in`.dimigo.dimigoin.ui.item

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.UserModel
import android.content.Context

data class NoticeItem(
    val title: String?,
    val content: String,
    val author: UserModel?
) {
    companion object {
        fun getFailedNoticeItem(context: Context): NoticeItem {
            val failedNoticeString = context.getString(R.string.notice_failed_message)
            return NoticeItem(null, failedNoticeString, null)
        }
    }
}
