package `in`.dimigo.dimigoin.data.usecase.ingang

import `in`.dimigo.dimigoin.data.model.IngangStatusModel
import `in`.dimigo.dimigoin.data.model.IngangTime

interface IngangUseCase {
    suspend fun getIngangStatus(): IngangStatusModel
    suspend fun applyIngang(time: IngangTime)
    suspend fun cancelIngang(time: IngangTime)
}
