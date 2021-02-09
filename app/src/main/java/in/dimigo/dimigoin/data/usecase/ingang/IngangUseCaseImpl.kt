package `in`.dimigo.dimigoin.data.usecase.ingang

import `in`.dimigo.dimigoin.data.model.IngangStatusModel
import `in`.dimigo.dimigoin.data.model.IngangTime
import `in`.dimigo.dimigoin.data.service.DimigoinService
import retrofit2.await

class IngangUseCaseImpl(private val service: DimigoinService) : IngangUseCase {

    override suspend fun getIngangStatus(): IngangStatusModel {
        return service.getIngangStatus().await()
    }

    override suspend fun applyIngang(time: IngangTime) {
        service.applyIngang(time).await()
    }

    override suspend fun cancelIngang(time: IngangTime) {
        service.cancelIngang(time).await()
    }
}
