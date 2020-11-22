package `in`.dimigo.dimigoin.ui.widget

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.MealModel
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import `in`.dimigo.dimigoin.ui.main.fragment.meal.MealTime
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import kotlinx.coroutines.*
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class NextMealWidget : AppWidgetProvider(), KoinComponent {
    private val mealUseCase: MealUseCase by inject()
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        scope.launch {
            val todayMeal = try {
                mealUseCase.getTodaysMeal()
            } catch (e: Exception) {
                e.printStackTrace()
                MealModel.getFailedMealModel(context)
            }
            for (appWidgetId in appWidgetIds) withContext(Dispatchers.Main) {
                updateAppWidget(context, appWidgetManager, appWidgetId, todayMeal)
            }
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        todayMeal: MealModel
    ) {
        val mealTime = MealTime.getCurrentMealTime()
        val mealTimeString = context.getString(mealTime.stringId)
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_next_meal).apply {
            setTextViewText(R.id.mealTime, mealTimeString)
            setTextViewText(R.id.mealContent, todayMeal.getMeal(mealTime))
        }
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }

    override fun onDisabled(context: Context?) {
        scope.cancel()
    }
}
