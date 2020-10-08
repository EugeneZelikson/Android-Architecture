package com.arch.example.base

import retrofit2.HttpException

data class Data<out T>(
    val status: Status,
    val message: String = "",
    val data: T? = null,
    val statusCode: Int = 0
) {

    enum class Status {
        SUCCESS, LOADING, ERROR
    }

    companion object {

        fun <T> success(data: T): Data<T> {
            return Data(Status.SUCCESS, data = data)
        }

        fun <T> loading(): Data<T> {
            return Data(Status.LOADING)
        }

        fun <T> error(throwable: Throwable): Data<T> {
            return if (throwable is HttpException) {
                Data(
                    Status.ERROR,
                    message = if (throwable.message == null) "" else throwable.message!!,
                    statusCode = throwable.code()
                )
            } else {
                Data(Status.ERROR, message = if (throwable.message == null) "" else throwable.message!!)
            }
        }
    }
}