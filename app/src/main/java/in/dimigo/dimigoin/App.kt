package `in`.dimigo.dimigoin

import `in`.dimigo.dimigoin.di.startKoin
import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }
}
