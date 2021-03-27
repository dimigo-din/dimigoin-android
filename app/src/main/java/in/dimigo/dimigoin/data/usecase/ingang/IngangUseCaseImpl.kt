package `in`.dimigo.dimigoin.data.usecase.ingang

import `in`.dimigo.dimigoin.data.model.IngangTime
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.Result
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.data.util.safeApiCall
import `in`.dimigo.dimigoin.ui.item.IngangStatusItem
import retrofit2.await

class IngangUseCaseImpl(
    private val service: DimigoinService,
    userDataStore: UserDataStore
) : IngangUseCase {
    private val userData = userDataStore.requireUserData()

    override suspend fun getIngangStatus(): Result<IngangStatusItem> {
        return safeApiCall {
            service.getIngangStatus().await().toIngangStatusItem(userData.idx)
        }
    }

    override suspend fun applyIngang(time: IngangTime): Result<Unit> {
        return safeApiCall {
            service.applyIngang(time).await()
        }
    }

    override suspend fun cancelIngang(time: IngangTime): Result<Unit> {
        return safeApiCall {
            service.cancelIngang(time).await()
        }
    }
}
