package `in`.dimigo.dimigoin.data.util

import `in`.dimigo.dimigoin.data.model.UserModel

class UserDataStore(private val sharedPreferencesManager: SharedPreferencesManager) {
    var userData: UserModel? = null
        private set
        get() {
            if (field == null) {
                field = sharedPreferencesManager.userData
            }
            return field
        }

    fun requireUserData(): UserModel {
        return userData ?: throw Exception("User Data is not initialized")
    }
}
