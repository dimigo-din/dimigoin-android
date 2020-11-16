package `in`.dimigo.dimigoin.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val jwtToken: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val isAuthorizationHeaderExists = chain.request().header("Authorization") != null

        val request = if (jwtToken == null || isAuthorizationHeaderExists) {
            chain.request()
        } else {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $jwtToken")
                .build()
        }
        return chain.proceed(request)
    }
}
