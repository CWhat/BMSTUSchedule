package ru.crazy_what.bmstu_shedule.common

import retrofit2.HttpException
import retrofit2.Response

sealed class ResponseResult<T> {
    data class Success<T>(val data: T) : ResponseResult<T>()
    data class Error<T>(val message: String) : ResponseResult<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> error(message: String) = Error<T>(message)
    }
}

// Сделано на основе
// https://proandroiddev.com/modeling-retrofit-responses-with-sealed-classes-and-coroutines-9d6302077dfe

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>,
): ResponseResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            ResponseResult.success(body)
        } else {
            ResponseResult.error("${response.code()} code error: ${response.message()}")
        }
    } catch (e: HttpException) {
        ResponseResult.error("${e.code()} code error: ${e.message()}")
    } catch (e: Throwable) {
        ResponseResult.error(e.message ?: e.toString())
    }
}
