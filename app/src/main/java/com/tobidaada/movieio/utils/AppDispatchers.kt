package com.tobidaada.movieio.utils

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface AppDispatchers {
    fun io(): CoroutineContext
    fun main(): CoroutineContext
    fun unconfined(): CoroutineContext

}

class AppDispatchersImpl: AppDispatchers {

    override fun io(): CoroutineContext = Dispatchers.IO

    override fun main(): CoroutineContext = Dispatchers.Main

    override fun unconfined(): CoroutineContext = Dispatchers.Unconfined
}