package `in`.dimigo.dimigoin.data.usecase.fcm

import `in`.dimigo.dimigoin.data.model.FcmTokenUploadRequestModel
import `in`.dimigo.dimigoin.data.service.DimigoinService
import retrofit2.await

class FcmUseCaseImpl(private val service: DimigoinService) : FcmUseCase {
    override suspend fun uploadFcmToken(token: String) {
        service.uploadFcmToken(FcmTokenUploadRequestModel(token)).await()
    }

    override suspend fun deleteFcmToken(token: String) {
        service.deleteFcmToken(FcmTokenUploadRequestModel(token)).await()
    }
}
