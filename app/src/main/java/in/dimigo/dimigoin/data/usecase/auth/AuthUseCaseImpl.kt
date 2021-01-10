package `in`.dimigo.dimigoin.data.usecase.auth

import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.model.AuthModel
import `in`.dimigo.dimigoin.data.model.LoginRequestModel
import `in`.dimigo.dimigoin.di.KEY_ACCESS_TOKEN
import `in`.dimigo.dimigoin.di.KEY_REFRESH_TOKEN
import android.content.SharedPreferences
import androidx.core.content.edit
import retrofit2.await

class AuthUseCaseImpl(
    private val service: DimigoinService,
    private val sharedPreferences: SharedPreferences
) : AuthUseCase {
    override suspend fun tryLogin(loginRequestModel: LoginRequestModel) = try {
        val authModel = service.login(loginRequestModel).await()
        saveAuthModel(authModel)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    override suspend fun tryTokenRefresh(): Boolean {
        try {
            val refreshToken = sharedPreferences.getString(KEY_REFRESH_TOKEN, null) ?: return false
            val authModel = service.refreshToken("Bearer $refreshToken").await()
            saveAuthModel(authModel)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    private fun saveAuthModel(authModel: AuthModel) {
        sharedPreferences.edit {
            putString(KEY_ACCESS_TOKEN, authModel.accessToken)
            putString(KEY_REFRESH_TOKEN, authModel.refreshToken)
        }
    }
}
