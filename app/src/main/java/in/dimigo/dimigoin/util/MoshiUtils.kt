package `in`.dimigo.dimigoin.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import java.util.*

fun buildMoshi(): Moshi =
    Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()

inline fun <reified T> String?.toObject(): T? {
    if (this == null) return null
    val moshi = buildMoshi()
    return moshi.adapter(T::class.java).fromJson(this)
}

inline fun <reified T> T.toJsonString(): String {
    val moshi = buildMoshi()
    return moshi.adapter(T::class.java).toJson(this)
}
