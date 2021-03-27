package `in`.dimigo.dimigoin.data.usecase.fcm

import `in`.dimigo.dimigoin.data.model.FcmTokenUploadRequestModel
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.Result
import `in`.dimigo.dimigoin.data.util.safeApiCall
import retrofit2.await

class FcmUseCaseImpl(private val service: DimigoinService) : FcmUseCase {
    override suspend fun uploadFcmToken(token: String): Result<Unit> {
        return safeApiCall {
            service.uploadFcmToken(FcmTokenUploadRequestModel(token)).await()
        }
    }

    override suspend fun deleteFcmToken(token: String): Result<Unit> {
        return safeApiCall {
            service.deleteFcmToken(FcmTokenUploadRequestModel(token)).await()
        }
    }
}
