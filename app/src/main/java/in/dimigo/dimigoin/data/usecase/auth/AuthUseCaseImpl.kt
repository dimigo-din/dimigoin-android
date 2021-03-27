package `in`.dimigo.dimigoin.data.usecase.auth

import `in`.dimigo.dimigoin.data.model.LoginRequestModel
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.usecase.fcm.FcmUseCase
import `in`.dimigo.dimigoin.data.util.Result
import `in`.dimigo.dimigoin.data.util.SharedPreferencesManager
import `in`.dimigo.dimigoin.data.util.safeApiCall
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import retrofit2.await

class AuthUseCaseImpl(
    private val service: DimigoinService,
    private val fcmUseCase: FcmUseCase,
    private val sharedPreferencesManager: SharedPreferencesManager
) : AuthUseCase {
    override suspend fun tryLogin(loginRequestModel: LoginRequestModel): Result<Unit> = safeApiCall {
        val authModel = service.login(loginRequestModel).await()
        sharedPreferencesManager.saveAuthModel(authModel)
        val token = getFcmToken()
        fcmUseCase.uploadFcmToken(token)
    }

    override suspend fun logout() {
        val token = getFcmToken()
        fcmUseCase.deleteFcmToken(token)
        FirebaseMessaging.getInstance().deleteToken().await()
        sharedPreferencesManager.clear()
    }

    private suspend fun getFcmToken() = FirebaseMessaging.getInstance().token.await()
}
