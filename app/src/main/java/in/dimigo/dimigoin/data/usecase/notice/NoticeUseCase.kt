package `in`.dimigo.dimigoin.data.usecase.notice

import `in`.dimigo.dimigoin.data.util.Result
import `in`.dimigo.dimigoin.ui.item.NoticeItem

interface NoticeUseCase {
    val failedNotice: NoticeItem

    suspend fun getNotice(): Result<List<NoticeItem>>
}
