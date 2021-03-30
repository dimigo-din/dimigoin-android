package `in`.dimigo.dimigoin.data.util

import com.squareup.moshi.Moshi

fun getMoshi(): Moshi = Moshi.Builder().build()

inline fun <reified T> String?.toObject(): T? {
    if (this == null) return null
    val moshi = getMoshi()
    return moshi.adapter(T::class.java).fromJson(this)
}

inline fun <reified T> T.toJsonString(): String {
    val moshi = getMoshi()
    return moshi.adapter(T::class.java).toJson(this)
}
