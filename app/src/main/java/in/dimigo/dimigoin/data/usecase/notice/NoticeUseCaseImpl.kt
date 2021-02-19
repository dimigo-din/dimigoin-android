package `in`.dimigo.dimigoin.data.usecase.notice

import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.ui.item.NoticeItem
import retrofit2.await

class NoticeUseCaseImpl(val service: DimigoinService, override val failedNotice: NoticeItem) : NoticeUseCase {

    override suspend fun getNotice(): NoticeItem {
        val notices = service.getCurrentNotices().await().notices
        if (notices.isEmpty()) throw Exception("Notices not found")
        val notice = notices.joinToString("\n\n") {
            it.content
        }
        return NoticeItem(notice)
    }
}
