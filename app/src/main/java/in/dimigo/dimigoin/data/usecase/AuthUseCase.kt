package `in`.dimigo.dimigoin.data.usecase

import `in`.dimigo.dimigoin.data.model.AuthModel
import `in`.dimigo.dimigoin.data.model.LoginRequestModel

interface AuthUseCase {
    suspend fun login(loginRequestModel: LoginRequestModel): AuthModel
}
