package `in`.dimigo.dimigoin.ui.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_DATE_CHANGED
import android.content.IntentFilter
import androidx.lifecycle.LiveData
import java.time.LocalDate

class DateChangedLiveData(private val context: Context) : LiveData<LocalDate>(LocalDate.now()) {
    private val intentFiler = IntentFilter(ACTION_DATE_CHANGED)
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            postValue(LocalDate.now())
        }
    }

    override fun onActive() {
        super.onActive()
        context.registerReceiver(receiver, intentFiler)
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(receiver)
    }
}
