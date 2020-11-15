package `in`.dimigo.dimigoin.data.model

import `in`.dimigo.dimigoin.ui.main.fragment.meal.MealTime

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
}
