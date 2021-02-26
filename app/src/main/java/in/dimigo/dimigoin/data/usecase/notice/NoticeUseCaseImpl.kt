package `in`.dimigo.dimigoin.data.usecase.notice

import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.ui.item.NoticeItem
import retrofit2.await

class NoticeUseCaseImpl(val service: DimigoinService, override val failedNotice: NoticeItem) : NoticeUseCase {

    override suspend fun getNotice(): List<NoticeItem> {
        return service.getCurrentNotices().await().notices.map { NoticeItem(it.title, it.content, it.author) }
    }
}
