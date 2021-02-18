package `in`.dimigo.dimigoin.di

import `in`.dimigo.dimigoin.data.usecase.attendance.AttendanceUseCase
import `in`.dimigo.dimigoin.data.usecase.attendance.AttendanceUseCaseImpl
import `in`.dimigo.dimigoin.data.usecase.auth.AuthUseCase
import `in`.dimigo.dimigoin.data.usecase.auth.AuthUseCaseImpl
import `in`.dimigo.dimigoin.data.usecase.ingang.IngangUseCase
import `in`.dimigo.dimigoin.data.usecase.ingang.IngangUseCaseImpl
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCaseImpl
import `in`.dimigo.dimigoin.data.usecase.notice.NoticeUseCase
import `in`.dimigo.dimigoin.data.usecase.notice.NoticeUseCaseImpl
import `in`.dimigo.dimigoin.data.usecase.timetable.TimetableUseCase
import `in`.dimigo.dimigoin.data.usecase.timetable.TimetableUseCaseImpl
import `in`.dimigo.dimigoin.data.usecase.user.UserUseCase
import `in`.dimigo.dimigoin.data.usecase.user.UserUseCaseImpl
import `in`.dimigo.dimigoin.data.util.SharedPreferencesManager
import `in`.dimigo.dimigoin.ui.attendance.AttendanceViewModel
import `in`.dimigo.dimigoin.ui.item.MealItem
import `in`.dimigo.dimigoin.ui.item.NoticeItem
import `in`.dimigo.dimigoin.ui.login.LoginViewModel
import `in`.dimigo.dimigoin.ui.main.MainViewModel
import `in`.dimigo.dimigoin.ui.main.fragment.card.CardViewModel
import `in`.dimigo.dimigoin.ui.main.fragment.ingang.IngangViewModel
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
    single<IngangUseCase> { IngangUseCaseImpl(get()) }
    single<TimetableUseCase> { TimetableUseCaseImpl(get()) }
    single<AttendanceUseCase> { AttendanceUseCaseImpl(get()) }
    single<NoticeUseCase> { NoticeUseCaseImpl(get(), NoticeItem.getFailedNoticeItem(androidContext())) }

    viewModel { LoginViewModel(get(), get()) }
    viewModel { MainViewModel() }
    viewModel { MainFragmentViewModel(get(), get(), get()) }
    viewModel { MealViewModel(get()) }
    viewModel { TimetableViewModel(get()) }
    viewModel { CardViewModel() }
    viewModel { IngangViewModel(get()) }
    viewModel { AttendanceViewModel(get()) }

    single { SharedPreferencesManager(androidContext()) }
}
