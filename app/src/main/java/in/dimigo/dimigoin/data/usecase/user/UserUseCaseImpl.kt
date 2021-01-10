package `in`.dimigo.dimigoin.data.usecase.user

import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.model.UserModel
import retrofit2.await

class UserUseCaseImpl(private val service: DimigoinService) : UserUseCase {
    override suspend fun getMyInfo(): UserModel {
        return service.getMyInfo().await().identity
    }
}
