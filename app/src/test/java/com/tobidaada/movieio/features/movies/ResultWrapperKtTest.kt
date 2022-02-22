package com.tobidaada.movieio.features.movies

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.common.truth.Truth.assertThat

@RunWith(JUnit4::class)
class ResultWrapperKtTest  {

    @Test
    fun transformResponse_withSuccessInput_returnsProperResultWrapper() {
        // given a success result wrapper object
        val value = 1
        val response = ResultWrapper.Success(value)

        // when the response is transformed
        val transformedResponse = transformResponse(response) { value }

        // then the original response object and transformed response object should
        // be of the same type
        assertThat(transformedResponse is ResultWrapper.Success).isTrue()
    }

    @Test
    fun transformResponse_withErrorInput_returnsProperResultWrapper() {
        // given an error result wrapper object
        val message = "Error"
        val response = ResultWrapper.Error(message)

        // when the response is transformed
        val transformedResponse = transformResponse(response) { message }

        // then the original response object and transformed response object should
        // be of the same type
        assertThat(transformedResponse is ResultWrapper.Error).isTrue()
    }

}