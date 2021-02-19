package `in`.dimigo.dimigoin.data.usecase.fcm

interface FcmUseCase {
    suspend fun uploadFcmToken(token: String)
    suspend fun deleteFcmToken(token: String)
}
