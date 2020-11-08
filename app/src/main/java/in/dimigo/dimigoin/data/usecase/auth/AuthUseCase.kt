package `in`.dimigo.dimigoin.data.usecase.auth

import `in`.dimigo.dimigoin.data.model.AuthModel
import `in`.dimigo.dimigoin.data.model.LoginRequestModel

interface AuthUseCase {
    suspend fun login(loginRequestModel: LoginRequestModel): AuthModel
    suspend fun refreshToken(refreshToken: String): AuthModel
}
