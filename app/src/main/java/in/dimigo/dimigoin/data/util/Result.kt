package `in`.dimigo.dimigoin.data.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class Result<out T> {
    fun onSuccess(block: (T) -> Unit): Result<T> {
        if (this is Success) {
            block(value)
        }
        return this
    }

    fun onFailure(block: (Throwable) -> Unit): Result<T> {
        if (this is Failure) {
            block(throwable)
        }
        return this
    }
}

data class Success<out T>(val value: T) : Result<T>()
data class Failure(val throwable: Throwable) : Result<Nothing>()

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> = withContext(Dispatchers.IO) {
    return@withContext try {
        val result = apiCall()
        Success(result)
    } catch (e: Throwable) {
        e.printStackTrace()
        Failure(e)
    }
}
