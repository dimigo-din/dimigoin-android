package `in`.dimigo.dimigoin.data.util

import `in`.dimigo.dimigoin.data.util.TokenAuthenticator.Companion.AUTHORIZATION_HEADER
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthorizationInterceptor(private val sharedPreferencesManager: SharedPreferencesManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (isAuthHeaderExists(request)) return chain.proceed(request)
        val authorizedRequest = getAuthorizedRequest(request)
        return chain.proceed(authorizedRequest)
    }

    private fun getAuthorizedRequest(request: Request): Request {
        val accessToken = sharedPreferencesManager.accessToken
        return if (accessToken == null) request
        else request.newBuilder()
            .addHeader(AUTHORIZATION_HEADER, "Bearer $accessToken")
            .build()
    }

    private fun isAuthHeaderExists(request: Request) = request.header(AUTHORIZATION_HEADER) != null
}
