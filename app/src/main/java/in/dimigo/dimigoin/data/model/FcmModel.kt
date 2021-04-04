package `in`.dimigo.dimigoin.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FcmTokenUploadRequestModel(val deviceToken: String)
