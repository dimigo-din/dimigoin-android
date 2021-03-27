package `in`.dimigo.dimigoin.data.usecase.user

import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.Result
import `in`.dimigo.dimigoin.data.util.SharedPreferencesManager
import `in`.dimigo.dimigoin.data.util.safeApiCall
import retrofit2.await

class UserUseCaseImpl(
    private val service: DimigoinService,
    private val sharedPreferencesManager: SharedPreferencesManager
) : UserUseCase {

    override suspend fun storeUserData(): Result<Unit> = safeApiCall {
        val userModel = service.getMyInfo().await().identity
        sharedPreferencesManager.userData = userModel
    }
}
