package `in`.dimigo.dimigoin.data.usecase.user

import `in`.dimigo.dimigoin.data.model.UserModel

interface UserUseCase {
    suspend fun getMyInfo(): UserModel
}
