package `in`.dimigo.dimigoin.di

import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.AuthorizationInterceptor
import `in`.dimigo.dimigoin.data.util.TokenAuthenticator
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { AuthorizationInterceptor(get()) }
    single { TokenAuthenticator(get()) }
    single { buildOkHttpClient(get(), get()) }
    single { buildRetrofit(get()) }

    single {
        val service = get<Retrofit>().create(DimigoinService::class.java)
        TokenAuthenticator.dimigoinService = service
        return@single service
    }
}

private fun buildOkHttpClient(
    interceptor: AuthorizationInterceptor,
    authenticator: TokenAuthenticator
) = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .authenticator(authenticator)
    .build()

private fun buildRetrofit(httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(DimigoinService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
}
