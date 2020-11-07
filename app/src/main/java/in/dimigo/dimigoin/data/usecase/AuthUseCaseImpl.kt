package `in`.dimigo.dimigoin.data.usecase

import `in`.dimigo.dimigoin.data.api.DimigoinApi
import `in`.dimigo.dimigoin.data.model.AuthModel
import `in`.dimigo.dimigoin.data.model.LoginRequestModel
import retrofit2.await

class AuthUseCaseImpl(private val api: DimigoinApi) : AuthUseCase {
    override suspend fun login(loginRequestModel: LoginRequestModel): AuthModel {
        return api.login(loginRequestModel).await()
    }
}
