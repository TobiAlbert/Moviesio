package com.tobidaada.movieio.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface DispatchersProvider {
    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
}

class CoroutineDispatcherProvider @Inject constructor() : DispatchersProvider {
    override fun io(): CoroutineDispatcher = Dispatchers.IO
    override fun main(): CoroutineDispatcher = Dispatchers.Main
}