package `in`.dimigo.dimigoin.data.usecase.user

import `in`.dimigo.dimigoin.data.util.Result

interface UserUseCase {
    suspend fun storeUserData(): Result<Unit>
}
