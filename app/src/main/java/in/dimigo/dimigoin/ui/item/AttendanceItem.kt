package `in`.dimigo.dimigoin.ui.item

import `in`.dimigo.dimigoin.data.model.PlaceType
import java.text.DecimalFormat

data class AttendanceItem(
    val number: Int,
    val name: String,
    val place: PlaceType
) {
    fun formatNumber(): String = DecimalFormat("##").format(number)
}
