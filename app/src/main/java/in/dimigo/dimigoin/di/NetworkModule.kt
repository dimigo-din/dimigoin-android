package `in`.dimigo.dimigoin.di

import `in`.dimigo.dimigoin.data.api.AuthorizationInterceptor
import `in`.dimigo.dimigoin.data.api.DimigoinApi
import `in`.dimigo.dimigoin.ui.login.LoginActivity.Companion.KEY_TOKEN
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        val accessToken = getAccessToken(get())
        AuthorizationInterceptor(accessToken)
    }
    single { buildOkHttpClient(get<AuthorizationInterceptor>()) }
    single { buildRetrofit(get()) }

    single { get<Retrofit>().create(DimigoinApi::class.java) }
}

private fun getAccessToken(sharedPreferences: SharedPreferences) = sharedPreferences.getString(KEY_TOKEN, null)

private fun buildOkHttpClient(interceptor: Interceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}

private fun buildRetrofit(httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(DimigoinApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
}
