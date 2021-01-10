package `in`.dimigo.dimigoin.data.util

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.await

class AuthorizationInterceptor(private val sharedPreferencesManager: SharedPreferencesManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (isAuthHeaderExists(request)) return chain.proceed(request)
        val authorizedRequest = runBlocking { getAuthorizedRequest(request) }
        return chain.proceed(authorizedRequest)
    }

    private suspend fun getAuthorizedRequest(request: Request): Request {
        val accessToken = refreshAndGetAccessToken()
        return if (accessToken == null) request
        else request.newBuilder()
            .addHeader(AUTHORIZATION_HEADER, "Bearer $accessToken")
            .build()
    }

    private fun isAuthHeaderExists(request: Request) = request.header(AUTHORIZATION_HEADER) != null

    private suspend fun refreshAndGetAccessToken(): String? {
        try {
            val refreshToken = sharedPreferencesManager.refreshToken ?: return null
            val authModel = DimigoinServiceHolder.dimigoinService.refreshToken("Bearer $refreshToken").await()
            sharedPreferencesManager.saveAuthModel(authModel)
            return authModel.accessToken
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
    }
}
