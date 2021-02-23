package `in`.dimigo.dimigoin.data.util

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtil {
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun DateTimeFormatter.from(date: Date): String =
        dateFormatter.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
}
