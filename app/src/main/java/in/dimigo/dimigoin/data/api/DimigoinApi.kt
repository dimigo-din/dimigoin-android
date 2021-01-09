package `in`.dimigo.dimigoin.data.api

object DimigoinApi {
    const val BASE_URL = "http://edison.dimigo.hs.kr"

    fun getProfileUrl(photo: String) = "https://api.dimigo.hs.kr/user_photo/$photo"
}
