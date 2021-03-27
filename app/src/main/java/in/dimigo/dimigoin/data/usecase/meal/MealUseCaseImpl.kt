package `in`.dimigo.dimigoin.data.usecase.meal

import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.DateUtil
import `in`.dimigo.dimigoin.data.util.Result
import `in`.dimigo.dimigoin.data.util.safeApiCall
import `in`.dimigo.dimigoin.ui.item.MealItem
import retrofit2.await
import java.time.DayOfWeek
import java.time.LocalDate

class MealUseCaseImpl(private val service: DimigoinService, override val failedMeal: MealItem) : MealUseCase {

    override suspend fun getTodaysMeal(): Result<MealItem> {
        return safeApiCall {
            service.getTodayMeal().await().toMealItem(failedMeal)
        }
    }

    override suspend fun getWeeklyMeal(): Result<List<MealItem>> = safeApiCall {
        val meals = service.getWeeklyMeal().await().meals
        val mealItems = MutableList(7) { failedMeal }
        val now = LocalDate.now()

        repeat(7) { index ->
            val date = now.with(DayOfWeek.MONDAY).plusDays(index.toLong())
            val formattedDate = date.format(DateUtil.dateFormatter)

            meals.find { it.date == formattedDate }?.let {
                mealItems[index] = it.toMealItem(failedMeal)
            }
        }
        return@safeApiCall mealItems
    }
}
