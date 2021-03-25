package `in`.dimigo.dimigoin.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.stopKoin

fun Context.startKoin() {
    org.koin.core.context.startKoin {
        androidLogger()
        androidContext(this@startKoin)
        modules(appModule, networkModule)
    }
}

fun Context.restartKoin() {
    stopKoin()
    startKoin()
}
