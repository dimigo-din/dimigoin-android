package `in`.dimigo.dimigoin.ui.widget

import `in`.dimigo.dimigoin.R
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class TodayMealWidget : AppWidgetProvider() {
    private var updateAlarm: WidgetUpdateAlarm? = null

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
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.mealListView)
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_today_meal)
        val intent = Intent(context, TodayMealWidgetService::class.java).apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }
        remoteViews.setRemoteAdapter(R.id.mealListView, intent)
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }

    override fun onEnabled(context: Context) {
        val intent = Intent(context, this::class.java).apply {
            action = ACTION_UPDATE_WIDGET
        }
        updateAlarm = WidgetUpdateAlarm(context, intent)
        updateAlarm?.startAlarm(listOf(0))
    }

    override fun onDisabled(context: Context) {
        updateAlarm?.stopAlarm()
    }

    companion object {
        private const val ACTION_UPDATE_WIDGET = "UPDATE_TODAY_MEAL_WIDGET"
    }
}
