package `in`.dimigo.dimigoin

import `in`.dimigo.dimigoin.di.appModule
import `in`.dimigo.dimigoin.di.networkModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule, networkModule)
        }
    }
}