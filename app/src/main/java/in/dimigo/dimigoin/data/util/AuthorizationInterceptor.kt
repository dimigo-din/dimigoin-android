package `in`.dimigo.dimigoin.data.util

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val sharedPreferencesManager: SharedPreferencesManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = sharedPreferencesManager.accessToken
        val isAuthorizationHeaderExists = chain.request().header("Authorization") != null

        val request = if (accessToken == null || isAuthorizationHeaderExists) {
            chain.request()
        } else {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        }
        return chain.proceed(request)
    }
}
