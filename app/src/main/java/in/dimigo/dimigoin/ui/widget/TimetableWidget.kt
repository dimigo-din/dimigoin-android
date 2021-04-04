package `in`.dimigo.dimigoin.ui.widget

import `in`.dimigo.dimigoin.R
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.widget.RemoteViews
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import java.time.DayOfWeek
import java.time.LocalDateTime

class TimetableWidget : AppWidgetProvider() {
    private val dayTextIds = listOf(
        R.id.text_monday,
        R.id.text_tuesday,
        R.id.text_wednesday,
        R.id.text_thursday,
        R.id.text_friday
    )

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
            if (id != null) setTextColor(id, getAttributeColor(context, R.attr.colorPrimary))
            else dayTextIds.forEach {
                setTextColor(it, getAttributeColor(context, R.attr.widgetTextColorDisabled))
            }
        }
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }

    @ColorInt
    private fun getAttributeColor(context: Context, @AttrRes attributeId: Int): Int {
        val typedValue = TypedValue()
        context.theme.applyStyle(R.style.ThemeOverlay_App_Widget, true)
        context.theme.resolveAttribute(attributeId, typedValue, true)
        return typedValue.data
    }
}
