package `in`.dimigo.dimigoin.data.usecase.config

interface ConfigUseCase {
    suspend fun getCurrentTimeCode(): String
}
