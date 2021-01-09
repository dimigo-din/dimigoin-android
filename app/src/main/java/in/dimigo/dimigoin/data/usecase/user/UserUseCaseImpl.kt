package `in`.dimigo.dimigoin.data.usecase.user

import `in`.dimigo.dimigoin.data.api.UserApi
import `in`.dimigo.dimigoin.data.model.UserModel
import retrofit2.await

class UserUseCaseImpl(private val api: UserApi) : UserUseCase {
    override suspend fun getMyInfo(): UserModel {
        return api.getMyInfo().await().identity
    }
}
