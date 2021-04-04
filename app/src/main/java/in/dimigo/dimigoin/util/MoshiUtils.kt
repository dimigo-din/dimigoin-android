package `in`.dimigo.dimigoin.util

import `in`.dimigo.dimigoin.data.model.IngangTime
import `in`.dimigo.dimigoin.data.model.PlaceType
import `in`.dimigo.dimigoin.data.model.UserType
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.EnumJsonAdapter
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import java.util.*

fun buildMoshi(): Moshi =
    Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .addEnumJsonAdapter(UserType::class.java, UserType.STUDENT)
        .addEnumJsonAdapter(IngangTime::class.java, IngangTime.NSS1)
        .addEnumJsonAdapter(PlaceType::class.java, PlaceType.ETC)
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

private fun <T : Enum<T>> Moshi.Builder.addEnumJsonAdapter(enumType: Class<T>, fallbackValue: T?): Moshi.Builder {
    return add(enumType, EnumJsonAdapter.create(enumType).withUnknownFallback(fallbackValue))
}
