package `in`.dimigo.dimigoin.ui.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*
import kotlin.collections.ArrayList

class WidgetUpdateAlarm(private val context: Context, private val intent: Intent) {
    private val alarmManager: AlarmManager by lazy {
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
    private val pendingIntents = ArrayList<PendingIntent>()

    init {
        intent.action = TodayMealWidget.ACTION_UPDATE_WIDGET
    }

    fun startAlarm(updateHours: List<Int>) = updateHours.forEach { updateHour ->
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, updateHour)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis < System.currentTimeMillis()) add(Calendar.DATE, 1)
        }
        val pendingIntent = getPendingIntent()
        pendingIntents.add(pendingIntent)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }


    fun stopAlarm() {
        for (pendingIntent in pendingIntents)
            alarmManager.cancel(pendingIntent)
        pendingIntents.clear()
    }

    private fun getPendingIntent() = PendingIntent.getBroadcast(
        context,
        System.currentTimeMillis().toInt(),
        intent,
        PendingIntent.FLAG_CANCEL_CURRENT
    )
}
