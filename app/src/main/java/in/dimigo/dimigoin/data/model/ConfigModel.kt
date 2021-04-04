package `in`.dimigo.dimigoin.data.model

import `in`.dimigo.dimigoin.R
import android.content.Context
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfigResponseModel(val config: ConfigModel)

@JsonClass(generateAdapter = true)
data class ConfigModel(
    val SELF_STUDY_TIMES: List<Map<String, TimePeriodModel>>,
    val MEAL_TIMES: List<MealTimesModel>
)

@JsonClass(generateAdapter = true)
data class TimePeriodModel(val start: TimeModel, val end: TimeModel) {
    fun toString(context: Context) = context.getString(
        R.string.time_range_format,
        start.hour,
        start.minute,
        end.hour,
        end.minute
    )
}

@JsonClass(generateAdapter = true)
data class MealTimesModel(
    val breakfast: TimeModel,
    val lunch: TimeModel,
    val dinner: TimeModel
)

@JsonClass(generateAdapter = true)
data class TimeModel(val hour: Int, val minute: Int) {
    fun toFormattedString(context: Context): String {
        val amPm = if (hour < 12) "오전" else "오후"
        return context.getString(R.string.time_format, amPm, hour, minute)
    }
}
