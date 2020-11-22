package `in`.dimigo.dimigoin.ui.main.fragment.meal

import `in`.dimigo.dimigoin.R
import androidx.annotation.StringRes
import java.util.*

enum class MealTime(@StringRes val stringId: Int) {
    BREAKFAST(R.string.breakfast),
    LUNCH(R.string.lunch),
    DINNER(R.string.dinner);

    companion object {
        fun getCurrentMealTime() = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in 0..8 -> BREAKFAST
            in 9..13 -> LUNCH
            in 14..19 -> DINNER
            else -> BREAKFAST
        }
    }
}
