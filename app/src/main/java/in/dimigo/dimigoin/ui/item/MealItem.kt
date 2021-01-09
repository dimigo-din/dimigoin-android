package `in`.dimigo.dimigoin.ui.item

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.ui.main.fragment.meal.MealTime
import android.content.Context

data class MealItem(
    val breakfast: String,
    val lunch: String,
    val dinner: String
) {
    fun getMeal(mealTime: MealTime) = when (mealTime) {
        MealTime.BREAKFAST -> breakfast
        MealTime.LUNCH -> lunch
        MealTime.DINNER -> dinner
    }

    companion object {
        fun getFailedMealItem(context: Context): MealItem {
            val failedMessage = context.getString(R.string.meal_failed_message)
            return MealItem(failedMessage, failedMessage, failedMessage)
        }
    }
}
