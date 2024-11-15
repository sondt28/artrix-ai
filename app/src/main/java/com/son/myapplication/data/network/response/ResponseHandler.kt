package com.son.myapplication.data.network.response

import com.son.myapplication.util.NetworkResult
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> handleApi(execute: suspend () -> Response<T>): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            NetworkResult.Error(response.code(), response.message())
        }
    } catch (e: HttpException) {
        NetworkResult.Error(e.code(), e.message())
    } catch (e: Exception) {
        NetworkResult.Exception(e)
    }
}
