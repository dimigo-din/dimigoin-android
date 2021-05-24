package `in`.dimigo.dimigoin.data.model

import `in`.dimigo.dimigoin.ui.item.MealItem
import com.squareup.moshi.JsonClass

private typealias MealList = List<String>

@JsonClass(generateAdapter = true)
data class WeeklyMealResponseModel(
    val meals: List<MealModel>
)

@JsonClass(generateAdapter = true)
data class MealResponseModel(
    val meal: MealModel
) {
    fun toMealItem(failedMealItem: MealItem) = meal.toMealItem(failedMealItem)
}

@JsonClass(generateAdapter = true)
data class MealModel(
    val breakfast: MealContent,
    val lunch: MealContent,
    val dinner: MealContent,
    val date: String
) {
    fun toMealItem(failedMealItem: MealItem) = MealItem(
        this.breakfast.content.stringify(failedMealItem.breakfast),
        this.lunch.content.stringify(failedMealItem.lunch),
        this.dinner.content.stringify(failedMealItem.dinner)
    )
}

@JsonClass(generateAdapter = true)
data class MealContent(
    val content: MealList,
    val image: String?
)

private fun MealList.stringify(failedMealString: String): String {
    return if (isNullOrEmpty()) failedMealString
    else joinToString(", ")
}
