package `in`.dimigo.dimigoin.ui.item

import `in`.dimigo.dimigoin.data.model.PlaceModel
import java.text.DecimalFormat

data class AttendanceItem(
    val number: Int?,
    val name: String,
    val place: PlaceModel?
) {
    fun formatNumber(): String = DecimalFormat("00").format(number)
}
