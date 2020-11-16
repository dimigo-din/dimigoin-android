package `in`.dimigo.dimigoin.ui.main.fragment.util

import java.text.SimpleDateFormat
import java.util.*

fun getFormattedToday(dateFormat: String): String {
    val time = Calendar.getInstance().time
    return SimpleDateFormat(dateFormat, Locale.KOREA).format(time)
}
