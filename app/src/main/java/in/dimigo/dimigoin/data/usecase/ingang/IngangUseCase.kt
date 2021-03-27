package `in`.dimigo.dimigoin.data.usecase.ingang

import `in`.dimigo.dimigoin.data.model.IngangTime
import `in`.dimigo.dimigoin.data.util.Result
import `in`.dimigo.dimigoin.ui.item.IngangStatusItem

interface IngangUseCase {
    suspend fun getIngangStatus(): Result<IngangStatusItem>
    suspend fun applyIngang(time: IngangTime): Result<Unit>
    suspend fun cancelIngang(time: IngangTime): Result<Unit>
}
