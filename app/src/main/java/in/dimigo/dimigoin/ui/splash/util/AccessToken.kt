package `in`.dimigo.dimigoin.ui.splash.util

import android.util.Base64
import org.json.JSONObject

class AccessToken(jwt: String) {
    private val tokenJson by lazy {
        decodeJWT(jwt)
    }

    private val expirationTime by lazy {
        tokenJson.getLong("exp")
    }

    fun isTokenExpired() = System.currentTimeMillis() / 1000 >= expirationTime

    private fun decodeJWT(jwt: String): JSONObject {
        val jwtSplits = jwt.split(".")
        return getJson(jwtSplits[1])
    }

    private fun getJson(encodedString: String): JSONObject {
        val decodedString = String(Base64.decode(encodedString, Base64.URL_SAFE))
        return JSONObject(decodedString)
    }
}
