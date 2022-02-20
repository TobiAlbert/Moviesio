package com.tobidaada.movieio.features.movies.utils

import com.tobidaada.movieio.features.movies.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T : Any> safeApiCall(
    coroutineDispatcher: CoroutineDispatcher,
    call: suspend () -> Response<T>
): ResultWrapper<T> = withContext(coroutineDispatcher) {
    return@withContext try {
        // invoke the api call
        val response = call.invoke()

        // if it's successful, then return a response wrapped in a success body
        if (response.isSuccessful) ResultWrapper.Success(response.body()!!)

        // handle other client errors
        ResultWrapper.Error(response.message())
    } catch (e: Exception) {
        // if there is an error, catch the error, and return the result
        val defaultMessage = "Unable to make request."
        val message: String? = when (e) {
            is IOException -> e.message
            is HttpException -> getErrorMessageFromHttpException(e)
            else -> defaultMessage
        }
        ResultWrapper.Error(message ?: defaultMessage)
    }
}

private fun getErrorMessageFromHttpException(exception: HttpException): String {
    return exception.message()
}
