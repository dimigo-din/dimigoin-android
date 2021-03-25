package `in`.dimigo.dimigoin.data.usecase.auth

import `in`.dimigo.dimigoin.data.model.LoginRequestModel
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.usecase.fcm.FcmUseCase
import `in`.dimigo.dimigoin.data.util.SharedPreferencesManager
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import retrofit2.await

class AuthUseCaseImpl(
    private val service: DimigoinService,
    private val fcmUseCase: FcmUseCase,
    private val sharedPreferencesManager: SharedPreferencesManager
) : AuthUseCase {
    override suspend fun tryLogin(loginRequestModel: LoginRequestModel) = try {
        val authModel = service.login(loginRequestModel).await()
        sharedPreferencesManager.saveAuthModel(authModel)
        uploadFcmToken()
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    override suspend fun logout() {
        try {
            val token = getFcmToken()
            fcmUseCase.deleteFcmToken(token)
            FirebaseMessaging.getInstance().deleteToken().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        sharedPreferencesManager.clear()
    }

    private suspend fun uploadFcmToken() {
        try {
            val token = getFcmToken()
            fcmUseCase.uploadFcmToken(token)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun getFcmToken() = FirebaseMessaging.getInstance().token.await()

}
