package `in`.dimigo.dimigoin.di

import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.AuthorizationInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { AuthorizationInterceptor(get()) }
    single { buildOkHttpClient(get<AuthorizationInterceptor>()) }
    single { buildRetrofit(get()) }

    single { get<Retrofit>().create(DimigoinService::class.java) }
}

private fun buildOkHttpClient(interceptor: Interceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}

private fun buildRetrofit(httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(DimigoinService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
}
