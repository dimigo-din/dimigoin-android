package `in`.dimigo.dimigoin.data.usecase.auth

import `in`.dimigo.dimigoin.data.model.LoginRequestModel
import `in`.dimigo.dimigoin.data.util.Result

interface AuthUseCase {
    suspend fun tryLogin(loginRequestModel: LoginRequestModel): Result<Unit>
    suspend fun logout(): Result<Unit>
}
