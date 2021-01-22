package `in`.dimigo.dimigoin.di

import `in`.dimigo.dimigoin.data.usecase.auth.AuthUseCase
import `in`.dimigo.dimigoin.data.usecase.auth.AuthUseCaseImpl
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCaseImpl
import `in`.dimigo.dimigoin.data.usecase.user.UserUseCase
import `in`.dimigo.dimigoin.data.usecase.user.UserUseCaseImpl
import `in`.dimigo.dimigoin.data.util.SharedPreferencesManager
import `in`.dimigo.dimigoin.ui.item.MealItem
import `in`.dimigo.dimigoin.ui.login.LoginViewModel
import `in`.dimigo.dimigoin.ui.main.fragment.main.MainFragmentViewModel
import `in`.dimigo.dimigoin.ui.main.fragment.meal.MealViewModel
import `in`.dimigo.dimigoin.ui.main.fragment.timetable.TimetableViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AuthUseCase> { AuthUseCaseImpl(get(), get()) }
    single<MealUseCase> { MealUseCaseImpl(get(), MealItem.getFailedMealItem(androidContext())) }
    single<UserUseCase> { UserUseCaseImpl(get()) }

    viewModel { LoginViewModel(get(), get()) }
    viewModel { MainFragmentViewModel(get()) }
    viewModel { MealViewModel(get()) }
    viewModel { TimetableViewModel() }

    single { SharedPreferencesManager(androidContext()) }
}
