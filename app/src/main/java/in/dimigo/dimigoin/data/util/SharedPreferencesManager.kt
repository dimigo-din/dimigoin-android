package `in`.dimigo.dimigoin.data.util

import `in`.dimigo.dimigoin.data.model.AuthModel
import `in`.dimigo.dimigoin.data.model.UserModel
import `in`.dimigo.dimigoin.util.toJsonString
import `in`.dimigo.dimigoin.util.toObject
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SharedPreferencesManager(private val context: Context) {
    private val sharedPreferences = createEncryptedSharedPreferences()

    var accessToken: String?
        get() = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
        set(value) = sharedPreferences.edit {
            putString(KEY_ACCESS_TOKEN, value)
        }

    var refreshToken: String?
        get() = sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
        set(value) = sharedPreferences.edit {
            putString(KEY_REFRESH_TOKEN, value)
        }

    var userData: UserModel?
        get() = sharedPreferences.getString(KEY_USER_DATA, null).toObject()
        set(value) = sharedPreferences.edit {
            putString(KEY_USER_DATA, value?.toJsonString())
        }

    fun saveAuthModel(authModel: AuthModel) {
        accessToken = authModel.accessToken
        refreshToken = authModel.refreshToken
    }

    fun clear() {
        accessToken = null
        refreshToken = null
        userData = null
    }

    private fun createEncryptedSharedPreferences(): SharedPreferences {
        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            "app_preferences",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "accessToken"
        private const val KEY_REFRESH_TOKEN = "refreshToken"
        private const val KEY_USER_DATA = "userData"
    }
}
