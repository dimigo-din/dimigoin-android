package `in`.dimigo.dimigoin.ui.widget

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.MealModel
import `in`.dimigo.dimigoin.data.usecase.meal.MealUseCase
import `in`.dimigo.dimigoin.ui.main.fragment.meal.MealTime
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.concurrent.CountDownLatch

class TodayMealWidgetService : RemoteViewsService() {
    private val mealUseCase: MealUseCase by inject()

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return MealRemoteViewsFactory(applicationContext, mealUseCase)
    }
}

private class MealRemoteViewsFactory(
    private val context: Context,
    private val mealUseCase: MealUseCase
) : RemoteViewsService.RemoteViewsFactory {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var todayMeal: MealModel? = null

    override fun getViewAt(position: Int): RemoteViews {
        fetchTodayMeal()

        val mealTime = MealTime.values()[position]
        val mealTimeString = context.getString(mealTime.stringId)
        return RemoteViews(context.packageName, R.layout.item_widget_today_meal).apply {
            setTextViewText(R.id.mealTime, mealTimeString)
            setTextViewText(R.id.mealContent, todayMeal?.getMeal(mealTime))
        }
    }

    private fun fetchTodayMeal() {
        if (todayMeal != null) return

        val countDownLatch = CountDownLatch(1)
        scope.launch {
            todayMeal = try {
                mealUseCase.getTodaysMeal()
            } catch (e: Exception) {
                e.printStackTrace()
                MealModel.getFailedMealModel(context)
            }
            countDownLatch.countDown()
        }

        countDownLatch.await()
    }

    override fun onCreate() {}

    override fun onDestroy() {
        scope.cancel()
    }

    override fun onDataSetChanged() {}
    override fun getLoadingView(): RemoteViews? = null
    override fun getCount() = 3
    override fun getViewTypeCount() = 1
    override fun getItemId(position: Int) = position.toLong()
    override fun hasStableIds() = true
}
