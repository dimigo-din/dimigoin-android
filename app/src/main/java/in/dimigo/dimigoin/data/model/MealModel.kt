package `in`.dimigo.dimigoin.data.model

import `in`.dimigo.dimigoin.ui.item.MealItem

private typealias MealList = List<String>

data class WeeklyMealResponseModel(
    val meals: List<MealModel>
)

data class MealResponseModel(
    val meal: MealModel
) {
    fun toMealItem(failedMealItem: MealItem) = meal.toMealItem(failedMealItem)
}

data class MealModel(
    val breakfast: MealList,
    val lunch: MealList,
    val dinner: MealList,
    val date: String
) {
    fun toMealItem(failedMealItem: MealItem) = MealItem(
        this.breakfast.stringify(failedMealItem.breakfast),
        this.lunch.stringify(failedMealItem.lunch),
        this.dinner.stringify(failedMealItem.dinner)
    )
}

private fun MealList.stringify(failedMealString: String): String {
    return if (isNullOrEmpty()) failedMealString
    else joinToString(", ")
}
