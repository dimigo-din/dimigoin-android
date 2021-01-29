package `in`.dimigo.dimigoin.data.model

import `in`.dimigo.dimigoin.ui.item.MealItem

private typealias MealList = List<String>

data class WeeklyMealResponseModel(
    val meals: List<MealModel>
)

data class MealResponseModel(
    val meal: MealModel
) {
    fun toMealItem() = MealItem(
        meal.breakfast.joinToString(),
        meal.lunch.joinToString(),
        meal.dinner.joinToString()
    )
}

data class MealModel(
    val breakfast: MealList,
    val lunch: MealList,
    val dinner: MealList,
)

private fun MealList.joinToString() = joinToString(", ")

fun MealModel.toMealItem() = MealItem(
    this.breakfast.joinToString(),
    this.lunch.joinToString(),
    this.dinner.joinToString()
)
