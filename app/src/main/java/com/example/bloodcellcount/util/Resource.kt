package com.example.bloodcellcount.util

import com.example.bloodcellcount.models.LoginResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>( data: T? = null,message: String?) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}


suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend() -> T): Resource<T> {
    return withContext(dispatcher) {
        try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    println( throwable . printStackTrace ())
                    Resource.Error(null,throwable.message)
                }
                is HttpException -> {
                    println( throwable . printStackTrace ())
                    val response = throwable.response()!!.errorBody()
                    val jsonObj = JSONObject(response!!.charStream().readText())
                    val loginResponse = LoginResponse(non_field_errors = jsonObj.getString("non_field_errors"))
                    Resource.Error(loginResponse as T,throwable.message())
                }
                else -> {
                    println( throwable . printStackTrace ())
                    Resource.Error(null,"error")
                }
            }
        }
    }
}

