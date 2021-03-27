package `in`.dimigo.dimigoin.data.usecase.fcm

import `in`.dimigo.dimigoin.data.util.Result

interface FcmUseCase {
    suspend fun uploadFcmToken(token: String): Result<Unit>
    suspend fun deleteFcmToken(token: String): Result<Unit>
}
