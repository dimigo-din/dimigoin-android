package `in`.dimigo.dimigoin.di

import `in`.dimigo.dimigoin.data.api.DimigoinApi
import `in`.dimigo.dimigoin.ui.login.LoginActivity.Companion.KEY_TOKEN
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { buildRetrofit(get()) }

    single { get<Retrofit>().create(DimigoinApi::class.java) }
}

private fun buildRetrofit(sharedPreferences: SharedPreferences): Retrofit {
    val token = sharedPreferences.getString(KEY_TOKEN, null)

    val httpClient = OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain ->
            val isAuthorizationHeaderExists = chain.request().header("Authorization") != null

            val request = if (token == null || isAuthorizationHeaderExists) {
                chain.request()
            } else {
                chain.request().newBuilder()
                    .addHeader("Authorization", token)
                    .build()
            }
            return@Interceptor chain.proceed(request)
        })
        .build()

    return Retrofit.Builder()
        .baseUrl(DimigoinApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
}
