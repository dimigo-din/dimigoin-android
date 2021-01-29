package `in`.dimigo.dimigoin.data.usecase.meal

import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.ui.item.MealItem
import retrofit2.await
import java.text.SimpleDateFormat
import java.util.*

class MealUseCaseImpl(private val service: DimigoinService, override val failedMeal: MealItem) : MealUseCase {

    override suspend fun getTodaysMeal(): MealItem {
        val calendar = Calendar.getInstance()
        return getMeal(calendar.time)
    }

    override suspend fun getWeeklyMeal(): List<MealItem> {
        val calendar = Calendar.getInstance()
        val meals = mutableListOf<MealItem>()
        calendar[Calendar.DAY_OF_WEEK] = Calendar.MONDAY

        for (i in 0..6) {
            meals.add(getMeal(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }

        return meals
    }

    override suspend fun getMeal(date: Date): MealItem {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
        return service.getMeal(formattedDate).await().toMealItem()
    }
}
