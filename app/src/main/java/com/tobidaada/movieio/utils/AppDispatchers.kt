package com.tobidaada.movieio.utils

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface AppDispatchers {
    fun io(): CoroutineContext
    fun main(): CoroutineContext
    fun unconfined(): CoroutineContext
}

class AppDispatchersImpl @Inject constructor(): AppDispatchers {

    override fun io(): CoroutineContext = Dispatchers.IO

    override fun main(): CoroutineContext = Dispatchers.Main

    override fun unconfined(): CoroutineContext = Dispatchers.Unconfined
}