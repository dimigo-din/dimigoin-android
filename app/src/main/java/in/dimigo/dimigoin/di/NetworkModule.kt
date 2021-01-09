package `in`.dimigo.dimigoin.di

import `in`.dimigo.dimigoin.data.api.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { AuthorizationInterceptor(get()) }
    single { buildOkHttpClient(get<AuthorizationInterceptor>()) }
    single { buildRetrofit(get()) }

    single { get<Retrofit>().create(AuthApi::class.java) }
    single { get<Retrofit>().create(MealApi::class.java) }
    single { get<Retrofit>().create(UserApi::class.java) }
}

const val KEY_ACCESS_TOKEN = "accessToken"
const val KEY_REFRESH_TOKEN = "refreshToken"

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
