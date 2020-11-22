package `in`.dimigo.dimigoin.ui.main.fragment.meal

import `in`.dimigo.dimigoin.R
import androidx.annotation.StringRes
import java.util.*

enum class MealTime(@StringRes val stringId: Int) {
    BREAKFAST(R.string.breakfast),
    LUNCH(R.string.lunch),
    DINNER(R.string.dinner);

    companion object {
        const val BREAKFAST_START_HOUR = 0
        const val LUNCH_START_HOUR = 9
        const val DINNER_START_HOUR = 14

        fun getCurrentMealTime() = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in BREAKFAST_START_HOUR until LUNCH_START_HOUR -> BREAKFAST
            in LUNCH_START_HOUR until DINNER_START_HOUR -> LUNCH
            in DINNER_START_HOUR until 24 -> DINNER
            else -> BREAKFAST
        }
    }
}
