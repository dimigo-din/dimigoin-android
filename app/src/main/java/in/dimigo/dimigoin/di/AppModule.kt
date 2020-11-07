package `in`.dimigo.dimigoin.di

import `in`.dimigo.dimigoin.data.usecase.AuthUseCase
import `in`.dimigo.dimigoin.data.usecase.AuthUseCaseImpl
import `in`.dimigo.dimigoin.ui.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<AuthUseCase> { AuthUseCaseImpl(get()) }

    viewModel { LoginViewModel(get()) }
}
