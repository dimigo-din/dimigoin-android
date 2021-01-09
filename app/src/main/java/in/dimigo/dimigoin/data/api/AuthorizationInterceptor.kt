package `in`.dimigo.dimigoin.data.api

import `in`.dimigo.dimigoin.di.KEY_ACCESS_TOKEN
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val sharedPreferences: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
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
