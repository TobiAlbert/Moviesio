package com.tobidaada.movieio.utils

import com.tobidaada.movieio.features.movies.ResultWrapper
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline isFetchSuccessful: (RequestType) -> Boolean,
    crossinline getErrorMessage: (RequestType) -> String,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = when {
        shouldFetch(data) -> {
            emit(ResultWrapper.Success(data))

            try {
                // make network request to fetch data
                val response = fetch()
                val isSuccessful = isFetchSuccessful(response)

                if (isSuccessful) {
                    // if successful, save response to local db
                    saveFetchResult(response)
                    query().map { ResultWrapper.Success(it) }
                } else {
                    // return captured error message to client
                    query().map { ResultWrapper.Error(getErrorMessage(response)) }
                }
            } catch (e: Throwable) {
                query().map { ResultWrapper.Error(e.message ?: "An Error Occurred") }
            }
        }
        else -> query().map { ResultWrapper.Success(it) }
    }

    emitAll(flow)
}