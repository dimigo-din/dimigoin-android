package `in`.dimigo.dimigoin.data.usecase.ingang

import `in`.dimigo.dimigoin.data.model.IngangTime
import `in`.dimigo.dimigoin.ui.item.IngangStatusItem

interface IngangUseCase {
    suspend fun getIngangStatus(): IngangStatusItem
    suspend fun applyIngang(time: IngangTime)
    suspend fun cancelIngang(time: IngangTime)
}
