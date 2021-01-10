package `in`.dimigo.dimigoin.data.usecase.auth

import `in`.dimigo.dimigoin.data.model.LoginRequestModel

interface AuthUseCase {
    suspend fun tryLogin(loginRequestModel: LoginRequestModel): Boolean
}
