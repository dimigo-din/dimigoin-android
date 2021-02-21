package `in`.dimigo.dimigoin.data.model

import `in`.dimigo.dimigoin.R
import android.content.Context

data class ConfigResponseModel(val config: ConfigModel)

data class ConfigModel(val SELF_STUDY_TIMES: List<Map<String, TimePeriodModel>>)

data class TimePeriodModel(val start: TimeModel, val end: TimeModel) {
    fun toString(context: Context) = context.getString(
        R.string.time_range_format,
        start.hour,
        start.minute,
        end.hour,
        end.minute
    )
}

data class TimeModel(val hour: Int, val minute: Int)
