package `in`.dimigo.dimigoin.data.model

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.ui.main.fragment.meal.MealTime
import android.content.Context

data class MealModel(
    val breakfast: String,
    val lunch: String,
    val dinner: String,
    val date: String
) {
    fun getMeal(mealTime: MealTime) = when (mealTime) {
        MealTime.BREAKFAST -> breakfast
        MealTime.LUNCH -> lunch
        MealTime.DINNER -> dinner
    }

    companion object {
        fun getFailedMealModel(context: Context): MealModel {
            val failedMessage = context.getString(R.string.meal_failed_message)
            return MealModel(failedMessage, failedMessage, failedMessage, "")
        }
    }
}
