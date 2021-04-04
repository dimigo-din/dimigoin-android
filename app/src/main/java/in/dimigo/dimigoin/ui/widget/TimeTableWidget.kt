package `in`.dimigo.dimigoin.ui.widget

import `in`.dimigo.dimigoin.R
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.time.DayOfWeek
import java.time.LocalDateTime

class TimeTableWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.subjectGridView)
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val date = LocalDateTime.now()
        val id: Int? = when (date.dayOfWeek) {
            DayOfWeek.MONDAY -> R.id.text_monday
            DayOfWeek.TUESDAY -> R.id.text_tuesday
            DayOfWeek.WEDNESDAY -> R.id.text_wednesday
            DayOfWeek.THURSDAY -> R.id.text_thursday
            DayOfWeek.FRIDAY -> R.id.text_friday
            else -> null
        }

        val remoteViews = RemoteViews(context.packageName, R.layout.widget_timetable)
        val intent = Intent(context, TimetableWidgetService::class.java).apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }
        remoteViews.apply {
            setRemoteAdapter(R.id.subjectGridView, intent)
            if (id != null) setTextColor(id, context.getColor(R.color.pink_400))
        }
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }
}
