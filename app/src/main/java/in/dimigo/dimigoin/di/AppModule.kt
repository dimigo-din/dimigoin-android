package `in`.dimigo.dimigoin.di

import `in`.dimigo.dimigoin.data.usecase.AuthUseCase
import `in`.dimigo.dimigoin.data.usecase.AuthUseCaseImpl
import `in`.dimigo.dimigoin.ui.login.LoginViewModel
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<AuthUseCase> { AuthUseCaseImpl(get()) }

    viewModel { LoginViewModel(get()) }

    single { createEncryptedSharedPreferences(androidContext()) }
}

private fun createEncryptedSharedPreferences(context: Context): SharedPreferences {
    val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    return EncryptedSharedPreferences.create(
        context,
        "app_preferences",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}
