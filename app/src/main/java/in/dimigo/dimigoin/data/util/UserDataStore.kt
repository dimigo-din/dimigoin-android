package `in`.dimigo.dimigoin.data.util

import `in`.dimigo.dimigoin.data.model.UserModel

class UserDataStore(sharedPreferencesManager: SharedPreferencesManager) {
    val userData: UserModel? by lazy {
        sharedPreferencesManager.userData
    }

    fun requireUserData(): UserModel {
        return userData ?: throw Exception("User Data is not initialized")
    }
}
