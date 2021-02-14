package `in`.dimigo.dimigoin.data.usecase.user

import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.UserDataStore
import retrofit2.await

class UserUseCaseImpl(private val service: DimigoinService) : UserUseCase {
    override suspend fun storeUserData() {
        val userModel = service.getMyInfo().await().identity
        UserDataStore.userData = userModel
    }
}
