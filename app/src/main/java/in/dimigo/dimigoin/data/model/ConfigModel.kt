package `in`.dimigo.dimigoin.data.model

data class ConfigResponseModel(val config: ConfigModel)

data class ConfigModel(val SELF_STUDY_TIMES: List<Map<String, TimePeriodModel>>)

data class TimePeriodModel(val start: TimeModel, val end: TimeModel)

data class TimeModel(val hour: Int, val minute: Int)
