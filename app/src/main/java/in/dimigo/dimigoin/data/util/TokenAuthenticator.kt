package `in`.dimigo.dimigoin.data.util

import `in`.dimigo.dimigoin.data.service.DimigoinService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.await

class TokenAuthenticator(private val sharedPreferencesManager: SharedPreferencesManager) : Authenticator {
    private var lastRefreshTimeMillis: Long? = null

    override fun authenticate(route: Route?, response: Response): Request? {
        if (isRecentlyRefreshed()) return null
        val newToken = runBlocking { refreshAndGetAccessToken() }
        return response.request().newBuilder()
            .header(AUTHORIZATION_HEADER, "Bearer $newToken")
            .build()
    }

    private fun isRecentlyRefreshed(): Boolean {
        val lastRefreshTemp = lastRefreshTimeMillis
        val now = System.currentTimeMillis()
        lastRefreshTimeMillis = now
        lastRefreshTemp ?: return false
        return now - lastRefreshTemp < ALLOW_RETRY_REFRESH_SECONDS * 1000
    }

    private suspend fun refreshAndGetAccessToken(): String? {
        try {
            val refreshToken = sharedPreferencesManager.refreshToken ?: return null
            val authModel = dimigoinService.refreshToken("Bearer $refreshToken").await()
            sharedPreferencesManager.saveAuthModel(authModel)
            return authModel.accessToken
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    companion object {
        lateinit var dimigoinService: DimigoinService
        const val AUTHORIZATION_HEADER = "Authorization"
        private const val ALLOW_RETRY_REFRESH_SECONDS = 60
    }
}
