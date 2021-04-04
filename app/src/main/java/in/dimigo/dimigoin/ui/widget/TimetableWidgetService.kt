package `in`.dimigo.dimigoin.ui.widget

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.usecase.timetable.TimetableUseCase
import `in`.dimigo.dimigoin.ui.item.SubjectItem
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import java.time.DayOfWeek
import java.time.LocalDateTime

class TimetableWidgetService : RemoteViewsService() {
    private val timetableUseCase: TimetableUseCase by inject()

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return TimetableRemoteViewsFactory(applicationContext, timetableUseCase)
    }
}

private class TimetableRemoteViewsFactory(
    private val context: Context,
    private val timetableUseCase: TimetableUseCase
) : RemoteViewsService.RemoteViewsFactory {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var timetable: List<SubjectItem?>? = null

    override fun getViewAt(position: Int): RemoteViews {
        val date = LocalDateTime.now()
        if (timetable == null) fetchTimetable()

        return RemoteViews(context.packageName, R.layout.item_widget_subject).apply {
            setTextViewText(R.id.text_subject, timetable?.get(position)?.name ?: "")
            if (DayOfWeek.from(date) == timetable?.get(position)?.dayOfWeek)
                setTextColor(R.id.text_subject, context.getColor(R.color.pink_400))
            else
                setTextColor(R.id.text_subject, context.getColor(R.color.grey_450))
        }
    }

    private fun fetchTimetable() = runBlocking {
        timetableUseCase.getWeeklyTimetable().onSuccess {
            timetable = it
        }.onFailure {
            timetable = null
        }
    }

    override fun onCreate() {}

    override fun onDestroy() {
        scope.cancel()
    }

    override fun onDataSetChanged() {
        timetable = null
    }

    override fun getLoadingView(): RemoteViews? = null
    override fun getCount() = 35
    override fun getViewTypeCount() = 1
    override fun getItemId(position: Int): Long = 0
    override fun hasStableIds() = true
}
