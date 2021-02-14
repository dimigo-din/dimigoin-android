package `in`.dimigo.dimigoin.data.usecase.notice

import `in`.dimigo.dimigoin.ui.item.NoticeItem

interface NoticeUseCase {
    val failedNotice: NoticeItem

    suspend fun getNotice(): NoticeItem
}
