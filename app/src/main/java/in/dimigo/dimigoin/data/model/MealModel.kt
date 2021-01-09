package `in`.dimigo.dimigoin.data.model

import `in`.dimigo.dimigoin.ui.item.MealItem

private typealias MealList = List<String>

data class MealModel(
    val breakfast: MealList,
    val lunch: MealList,
    val dinner: MealList,
) {
    fun toMealItem() = MealItem(breakfast.joinToString(), breakfast.joinToString(), breakfast.joinToString())
}

private fun MealList.joinToString() = joinToString(", ")
