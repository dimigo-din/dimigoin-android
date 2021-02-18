package `in`.dimigo.dimigoin.ui.item

import java.text.DecimalFormat

data class AttendanceItem(
    val number: Int,
    val name: String,
    val type: String
) {
    fun formatNumber() = DecimalFormat("##").format(number)
}
