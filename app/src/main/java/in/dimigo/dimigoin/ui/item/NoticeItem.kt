package `in`.dimigo.dimigoin.ui.item

import `in`.dimigo.dimigoin.R
import android.content.Context

data class NoticeItem(val content: String) {
    companion object {
        fun getFailedNoticeItem(context: Context): NoticeItem {
            val failedNoticeString = context.getString(R.string.notice_failed_message)
            return NoticeItem(failedNoticeString)
        }
    }
}
