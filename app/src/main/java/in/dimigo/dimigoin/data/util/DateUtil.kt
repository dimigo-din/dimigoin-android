package `in`.dimigo.dimigoin.data.util

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtil {
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    fun DateTimeFormatter.from(date: Date): String =
        this.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
}
