package com.tobidaada.movieio.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.tobidaada.movieio.features.movies.ResultWrapper
import com.tobidaada.movieio.features.movies.data.datasource.remote.MovieErrorResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

private const val DEFAULT_ERROR_MESSAGE = "Unable to make request."
suspend fun <T : Any> safeApiCall(
    coroutineDispatcher: CoroutineDispatcher,
    call: suspend () -> Response<T>
): ResultWrapper<T> = withContext(coroutineDispatcher) {
    return@withContext try {
        val response = call.invoke()

        if (response.isSuccessful) return@withContext ResultWrapper.Success(response.body()!!)

        // handle possible client errors
        ResultWrapper.Error(getHttpErrorMessage(response))
    } catch (e: Exception) {
        val message: String? = when (e) {
            is IOException -> e.message
            is HttpException -> e.message()
            else -> DEFAULT_ERROR_MESSAGE
        }

        ResultWrapper.Error(message ?: DEFAULT_ERROR_MESSAGE)
    }
}

private fun <T> getHttpErrorMessage(response: Response<T>): String {
    val jsonString = response.errorBody()?.string() ?: return DEFAULT_ERROR_MESSAGE

    return try {
        val movieErrorResponse: MovieErrorResponse =
            Gson().fromJson(jsonString, MovieErrorResponse::class.java)

        movieErrorResponse.message ?: DEFAULT_ERROR_MESSAGE
    } catch (e: JsonSyntaxException) {
        DEFAULT_ERROR_MESSAGE
    }
}
