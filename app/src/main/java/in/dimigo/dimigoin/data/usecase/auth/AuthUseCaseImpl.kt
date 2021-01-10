package `in`.dimigo.dimigoin.data.usecase.auth

import `in`.dimigo.dimigoin.data.model.LoginRequestModel
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.SharedPreferencesManager
import retrofit2.await

class AuthUseCaseImpl(
    private val service: DimigoinService,
    private val sharedPreferencesManager: SharedPreferencesManager
) : AuthUseCase {
    override suspend fun tryLogin(loginRequestModel: LoginRequestModel) = try {
        val authModel = service.login(loginRequestModel).await()
        sharedPreferencesManager.saveAuthModel(authModel)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    override suspend fun tryTokenRefresh(): Boolean {
        try {
            val refreshToken = sharedPreferencesManager.refreshToken ?: return false
            val authModel = service.refreshToken("Bearer $refreshToken").await()
            sharedPreferencesManager.saveAuthModel(authModel)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}
