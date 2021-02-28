package `in`.dimigo.dimigoin.data.usecase.config

import `in`.dimigo.dimigoin.data.model.ConfigModel
import `in`.dimigo.dimigoin.data.model.MealTimesModel
import `in`.dimigo.dimigoin.data.model.UserType
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.UserDataStore
import retrofit2.await
import java.time.LocalDateTime

class ConfigUseCaseImpl(val service: DimigoinService) : ConfigUseCase {
    private var cachedConfig: ConfigModel? = null

    /**
     * AFSC: AfterSchool
     * NSS: NightSelfStudy
     * @return "AFSC${time}", "NSS1${time}"
     */
    override suspend fun getCurrentTimeCode(): String {
        val grade =
            if (UserDataStore.userData.userType == UserType.STUDENT) UserDataStore.userData.grade
            else 1
        val timeEndMinutes = getConfig()
            .SELF_STUDY_TIMES[grade]
            .mapValues {
                it.value.end.hour * 60 + it.value.end.minute
            }
            .toList()
            .sortedBy { (_, minutes) -> minutes }

        val now = LocalDateTime.now()
        val currentMinutes = now.hour * 60 + now.minute

        val currentTime = timeEndMinutes.find { (_, endMinutes) ->
            currentMinutes <= endMinutes
        } ?: timeEndMinutes.last()
        return currentTime.first
    }

    override suspend fun getMealTimes(): MealTimesModel {
        return getConfig().MEAL_TIMES[UserDataStore.userData.grade]
    }

    private suspend fun getConfig(): ConfigModel {
        return cachedConfig ?: run {
            val config = service.getConfigs().await().config
            cachedConfig = config
            return@run config
        }
    }
}
