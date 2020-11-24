package `in`.dimigo.dimigoin.ui.widget

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.ui.main.fragment.meal.MealTime
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class NextMealWidget : AppWidgetProvider() {
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (intent.action == ACTION_UPDATE_WIDGET) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val mealWidget = ComponentName(context, this::class.java)
            onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(mealWidget))
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.nextMealListView)
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_next_meal)
        val intent = Intent(context, NextMealWidgetService::class.java).apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }
        remoteViews.setRemoteAdapter(R.id.nextMealListView, intent)
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }

    override fun onEnabled(context: Context) {
        val intent = Intent(context, this::class.java).apply {
            action = ACTION_UPDATE_WIDGET
        }
        updateAlarm = WidgetUpdateAlarm(context, intent)
        val widgetUpdateHours = listOf(
            MealTime.BREAKFAST_START_HOUR,
            MealTime.LUNCH_START_HOUR,
            MealTime.DINNER_START_HOUR
        )
        updateAlarm?.startAlarm(widgetUpdateHours)
    }

    override fun onDisabled(context: Context) {
        updateAlarm?.stopAlarm()
    }

    companion object {
        private const val ACTION_UPDATE_WIDGET = "UPDATE_NEXT_MEAL_WIDGET"
        private var updateAlarm: WidgetUpdateAlarm? = null
    }
}
