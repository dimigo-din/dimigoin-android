package `in`.dimigo.dimigoin.data.usecase.config

import `in`.dimigo.dimigoin.data.model.ConfigModel
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.UserDataStore
import retrofit2.await
import java.time.LocalDateTime

class ConfigUseCaseImpl(val service: DimigoinService) : ConfigUseCase {
    private var cachedConfig: ConfigModel? = null

    /**
     * @return "AFSC${time}", "NSS1${time}"
     * AFSC: AfterSchool
     * NSS: NightSelfStudy
     */
    override suspend fun getCurrentTimeCode(): String {
        val timeEndMinutes = getConfig()
            .SELF_STUDY_TIMES[UserDataStore.userData.grade]
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

    private suspend fun getConfig(): ConfigModel {
        return cachedConfig ?: run {
            val config = service.getConfigs().await().config
            cachedConfig = config
            return@run config
        }
    }
}