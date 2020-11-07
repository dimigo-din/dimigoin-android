package `in`.dimigo.dimigoin.di

import `in`.dimigo.dimigoin.data.api.DimigoinApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(DimigoinApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(DimigoinApi::class.java) }
}
